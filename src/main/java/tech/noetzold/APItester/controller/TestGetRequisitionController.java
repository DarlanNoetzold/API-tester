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

import java.util.*;

import tech.noetzold.APItester.model.TestGetRequisition;
import tech.noetzold.APItester.model.Result;
import tech.noetzold.APItester.service.TestGetRequisitionService;
import tech.noetzold.APItester.service.ResultService;
import tech.noetzold.APItester.service.UserService;
import tech.noetzold.APItester.tests.*;
import tech.noetzold.APItester.util.QueryStringParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/get")
public class TestGetRequisitionController {

    @Autowired
    TestGetRequisitionService testGetRequisitionService;

    @Autowired
    ResultService resultService;

    @Autowired
    UserService userService;

    @GetMapping("/getByUser/{login}")
    public ResponseEntity<Page<TestGetRequisition>> getAllByUser(HttpServletRequest request, HttpServletResponse response, Pageable pageable, @PathVariable("login") String login) {
        return new ResponseEntity<>(testGetRequisitionService.findByUser(pageable, login), HttpStatus.OK);
    }

    @DeleteMapping("remove/{id}")
    public void remove(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") Integer id) {
        testGetRequisitionService.deleteGetRequisitionById(id);
    }

    @PostMapping("/test")
    public ResponseEntity<TestGetRequisition> testGetEndpoint(
            @RequestBody TestGetRequisition testGetRequisition) {
        String url = testGetRequisition.getUrl();
        Map<String, String> parameters = QueryStringParser.parseQueryString(testGetRequisition.getParameters());
        Map<String, String> headers = QueryStringParser.parseQueryString(testGetRequisition.getHeaders());

        RequestSpecification request = RestAssured.given()
                .urlEncodingEnabled(false);

        if (!headers.isEmpty()) {
            request.headers(headers);
        }

        if (!parameters.isEmpty()) {
            request.params(parameters);
        }

        testGetRequisition.setResult(callTestsAndReturnResults(request,url,parameters, testGetRequisition, headers));

        testGetRequisition.setUser(userService.findUserByLogin(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()));

        testGetRequisitionService.saveService(testGetRequisition);

        return ResponseEntity.status(HttpStatus.OK).body(testGetRequisition);
    }

    private List<Result> callTestsAndReturnResults(RequestSpecification request, String url, Map<String, String> params, TestGetRequisition testGetRequisition, Map<String, String> headers){
        List<Result> testsResults = new ArrayList<>();

        SecurityTest securityTest = new SecurityTest();
        testsResults.add(resultService.saveService(securityTest.testGetSecureResponse(request, url)));

        SqlInjectionTest sqlInjectionTest = new SqlInjectionTest();
        testsResults.add(resultService.saveService(sqlInjectionTest.testGetSqlInjection(url, request, params)));

        CommandInjectionTest commandInjectionTest = new CommandInjectionTest();
        testsResults.add(resultService.saveService(commandInjectionTest.testGetCommandInjection(url, request, params)));

        XssTest xssTest = new XssTest();
        testsResults.add(resultService.saveService(xssTest.testGetXss(url, request, params)));

        DataValidationTest dataValidationTest = new DataValidationTest();
        testsResults.add(resultService.saveService(dataValidationTest.testGetDataValidation(url, request, params)));

        SendToGPT3 sendToGPT3Test = new SendToGPT3(testGetRequisition);
        testsResults.add(resultService.saveService(sendToGPT3Test.doGptGetTest()));

        PerformanceTest performanceTest = new PerformanceTest(testGetRequisition);
        List<Result> performanceTestResults = performanceTest.runGetTests(1, 100, params, headers);
        for (Result result: performanceTestResults) testsResults.add(resultService.saveService(result));

        return testsResults;

    }
}
