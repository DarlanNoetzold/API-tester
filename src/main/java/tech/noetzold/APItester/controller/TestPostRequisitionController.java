package tech.noetzold.APItester.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tech.noetzold.APItester.model.Result;
import tech.noetzold.APItester.model.TestGetRequisition;
import tech.noetzold.APItester.model.TestPostRequisition;
import tech.noetzold.APItester.model.User;
import tech.noetzold.APItester.service.ResultService;
import tech.noetzold.APItester.service.TestPostRequisitionService;
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
@RequestMapping("/post")
public class TestPostRequisitionController {

    @Autowired
    TestPostRequisitionService testPostRequisitionService;

    @Autowired
    ResultService resultService;

    @Autowired
    UserService userService;

    @GetMapping("/getByUser/{login}")
    public ResponseEntity<Page<TestPostRequisition>> getAllByUser(HttpServletRequest request, HttpServletResponse response, Pageable pageable, @PathVariable("login") String login) {
        return new ResponseEntity<>(testPostRequisitionService.findByUser(pageable, login), HttpStatus.OK);
    }

    @DeleteMapping("remove/{id}")
    public void remove(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") Integer id) {
        testPostRequisitionService.deleteGetRequisitionById(id);
    }

    @PostMapping("/test")
    public ResponseEntity<TestPostRequisition> testPostRequest(@RequestBody TestPostRequisition testPostRequisition) {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<Map<String, Object>> typeRef = new TypeReference<>() {};
        try {
            Map<String, Object> body = objectMapper.readValue(testPostRequisition.getBody(), typeRef);
            Map<String, String> headers = QueryStringParser.parseQueryString(testPostRequisition.getHeaders());

            RequestSpecification request = RestAssured.given();

            List<Result> testsResults = callTestsAndReturnResults(request, testPostRequisition.getUrl(), body, headers);

            User user = userService.findUserByLogin(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

            TestPostRequisition req = testPostRequisitionService.saveService(new TestPostRequisition(body, Calendar.getInstance(), testsResults, user));

            return ResponseEntity.status(HttpStatus.OK).body(req);

        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(testPostRequisition);
        }

    }

    private List<Result> callTestsAndReturnResults(RequestSpecification request, String url, Map<String, Object> body, Map<String, String> headers){
        List<Result> testsResults = new ArrayList<>();

        SecurityTest securityTest = new SecurityTest();
        testsResults.add(resultService.saveService(securityTest.testPostSecureResponse(request, url, body, headers)));

        SqlInjectionTest sqlInjectionTest = new SqlInjectionTest();
        testsResults.add(resultService.saveService(sqlInjectionTest.testPostSqlInjection(request, url, body, headers)));

        CommandInjectionTest commandInjectionTest = new CommandInjectionTest();
        testsResults.add(resultService.saveService(commandInjectionTest.testPostCommandInjection(request, url, body, headers)));

        XssTest xssTest = new XssTest();
        testsResults.add(resultService.saveService(xssTest.testPostXss(request, url, body, headers)));

        DataValidationTest dataValidationTest = new DataValidationTest();
        testsResults.add(resultService.saveService(dataValidationTest.testPostDataValidation(request, url, body, headers)));

        return testsResults;

    }
}
