package tech.noetzold.APItester.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tech.noetzold.APItester.model.Result;
import tech.noetzold.APItester.model.TestPutRequisition;
import tech.noetzold.APItester.model.User;
import tech.noetzold.APItester.service.ResultService;
import tech.noetzold.APItester.service.TestPutRequisitionService;
import tech.noetzold.APItester.service.UserService;
import tech.noetzold.APItester.tests.*;
import tech.noetzold.APItester.util.QueryStringParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/put")
public class TestPutRequisitionController {

    @Autowired
    TestPutRequisitionService testPutRequisitionService;

    @Autowired
    ResultService resultService;

    @Autowired
    UserService userService;

    @GetMapping("/getByUser/{login}")
    public ResponseEntity<Page<TestPutRequisition>> getAllByUser(HttpServletRequest request, HttpServletResponse response, Pageable pageable, @PathVariable("login") String login) {
        return new ResponseEntity<>(testPutRequisitionService.findByUser(pageable, login), HttpStatus.OK);
    }

    @DeleteMapping("remove/{id}")
    public void remove(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") Integer id) {
        testPutRequisitionService.deleteGetRequisitionById(id);
    }

    @PutMapping("/test")
    public ResponseEntity<TestPutRequisition> testPutRequest(@RequestBody TestPutRequisition testPutRequisition) {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<Map<String, Object>> typeRef = new TypeReference<>() {};
        try {
            Map<String, Object> body = objectMapper.readValue(testPutRequisition.getBody(), typeRef);
            Map<String, String> headers = QueryStringParser.parseQueryString(testPutRequisition.getHeaders());

            RequestSpecification request = RestAssured.given();

            List<Result> testsResults = callTestsAndReturnResults(request, testPutRequisition.getUrl(), body, headers, testPutRequisition);

            User user = userService.findUserByLogin(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

            TestPutRequisition req = testPutRequisitionService.saveService(new TestPutRequisition(body, Calendar.getInstance(), testsResults, user));

            return ResponseEntity.status(HttpStatus.OK).body(req);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(testPutRequisition);
        }

    }

    private List<Result> callTestsAndReturnResults(RequestSpecification request, String url, Map<String, Object> body, Map<String, String> headers, TestPutRequisition testPutRequisition){
        List<Result> testsResults = new ArrayList<>();

        SecurityTest securityTest = new SecurityTest();
        testsResults.add(resultService.saveService(securityTest.testPutSecureResponse(request, url, body, headers)));

        SqlInjectionTest sqlInjectionTest = new SqlInjectionTest();
        testsResults.add(resultService.saveService(sqlInjectionTest.testPutSqlInjection(request, url, body, headers)));

        CommandInjectionTest commandInjectionTest = new CommandInjectionTest();
        testsResults.add(resultService.saveService(commandInjectionTest.testPutCommandInjection(request, url, body, headers)));

        XssTest xssTest = new XssTest();
        testsResults.add(resultService.saveService(xssTest.testPutXss(request, url, body, headers)));

        DataValidationTest dataValidationTest = new DataValidationTest();
        testsResults.add(resultService.saveService(dataValidationTest.testPutDataValidation(request, url, body, headers)));
        if(testPutRequisition.isOnline()) {
            SendToGPT3 sendToGPT3Test = new SendToGPT3(testPutRequisition);
            testsResults.add(resultService.saveService(sendToGPT3Test.doGptPutTest()));
        }
        PerformanceTest performanceTest = new PerformanceTest(testPutRequisition);
        List<Result> performanceTestResults = performanceTest.runPutTests(1,1, body, headers);
        for (Result result: performanceTestResults) testsResults.add(resultService.saveService(result));

        return testsResults;

    }
}
