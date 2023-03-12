package tech.noetzold.APItester.controller;

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
import tech.noetzold.APItester.model.TestDeleteRequisition;
import tech.noetzold.APItester.service.ResultService;
import tech.noetzold.APItester.service.TestDeleteRequisitionService;
import tech.noetzold.APItester.service.UserService;
import tech.noetzold.APItester.tests.*;
import tech.noetzold.APItester.util.QueryStringParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/delete")
public class TestDeleteRequisitionController {
    @Autowired
    TestDeleteRequisitionService testDeleteRequisitionService;

    @Autowired
    ResultService resultService;

    @Autowired
    UserService userService;

    @DeleteMapping("/getByUser/{login}")
    public ResponseEntity<Page<TestDeleteRequisition>> getAllByUser(HttpServletRequest request, HttpServletResponse response, Pageable pageable, @PathVariable("login") String login) {
        return new ResponseEntity<>(testDeleteRequisitionService.findByUser(pageable, login), HttpStatus.OK);
    }

    @DeleteMapping("remove/{id}")
    public void remove(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") Integer id) {
        testDeleteRequisitionService.deleteDeleteRequisitionById(id);
    }

    @PostMapping("/test")
    public ResponseEntity<TestDeleteRequisition> testDeleteEndpoint(
            @RequestBody TestDeleteRequisition testDeleteRequisition) {
        String url = testDeleteRequisition.getUrl();
        Map<String, String> parameters = QueryStringParser.parseQueryString(testDeleteRequisition.getParameters());
        Map<String, String> headers = QueryStringParser.parseQueryString(testDeleteRequisition.getHeaders());

        RequestSpecification request = RestAssured.given()
                .urlEncodingEnabled(false);

        if (!headers.isEmpty()) {
            request.headers(headers);
        }

        if (!parameters.isEmpty()) {
            request.params(parameters);
        }

        testDeleteRequisition.setResult(callTestsAndReturnResults(request,url,parameters, testDeleteRequisition, headers));

        testDeleteRequisition.setUser(userService.findUserByLogin(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()));

        testDeleteRequisitionService.saveService(testDeleteRequisition);

        return ResponseEntity.status(HttpStatus.OK).body(testDeleteRequisition);
    }

    private List<Result> callTestsAndReturnResults(RequestSpecification request, String url, Map<String, String> params, TestDeleteRequisition testDeleteRequisition, Map<String, String> headers){
        List<Result> testsResults = new ArrayList<>();

        SecurityTest securityTest = new SecurityTest();
        testsResults.add(resultService.saveService(securityTest.testDeleteSecureResponse(request, url)));

        SqlInjectionTest sqlInjectionTest = new SqlInjectionTest();
        testsResults.add(resultService.saveService(sqlInjectionTest.testDeleteSqlInjection(url, request, params)));

        CommandInjectionTest commandInjectionTest = new CommandInjectionTest();
        testsResults.add(resultService.saveService(commandInjectionTest.testDeleteCommandInjection(url, request, params)));

        XssTest xssTest = new XssTest();
        testsResults.add(resultService.saveService(xssTest.testDeleteXss(url, request, params)));

        DataValidationTest dataValidationTest = new DataValidationTest();
        testsResults.add(resultService.saveService(dataValidationTest.testDeleteDataValidation(url, request, params)));

        SendToGPT3 sendToGPT3Test = new SendToGPT3(testDeleteRequisition);
        testsResults.add(resultService.saveService(sendToGPT3Test.doGptDeleteTest()));

        PerformanceTest performanceTest = new PerformanceTest(testDeleteRequisition);
        List<Result> performanceTestResults = performanceTest.runDeleteTests(1, 100, params, headers);
        for (Result result: performanceTestResults) testsResults.add(resultService.saveService(result));

        return testsResults;

    }
}
