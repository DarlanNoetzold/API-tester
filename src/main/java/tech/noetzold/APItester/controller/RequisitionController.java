package tech.noetzold.APItester.controller;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import io.restassured.RestAssured;
import tech.noetzold.APItester.model.Requisition;
import tech.noetzold.APItester.model.Result;
import tech.noetzold.APItester.service.RequisitionService;
import tech.noetzold.APItester.service.ResultService;
import tech.noetzold.APItester.tests.*;
import tech.noetzold.APItester.util.REQ_TYPE;
import tech.noetzold.APItester.util.TEST_TYPE;

@RestController
@RequestMapping("/api")
public class RequisitionController {

    @Autowired
    RequisitionService requisitionService;

    @Autowired
    ResultService resultService;

    @GetMapping("/get-request")
    public ResponseEntity<Requisition> testEndpoint(
            @RequestParam("url") String url,
            @RequestParam(value = "headers", required = false) Map<String, String> headers,
            @RequestParam(value = "params", required = false) Map<String, String> params
    ) {
        RequestSpecification request = RestAssured.given()
                .urlEncodingEnabled(false);

        if (headers != null && !headers.isEmpty()) {
            request.headers(headers);
        }

        if (params != null && !params.isEmpty()) {
            request.params(params);
        }

        List<Result> testsResults = new ArrayList<>();

        SecurityTest securityTest = new SecurityTest();
        testsResults.add(resultService.saveService(securityTest.testSecureResponse(request, url)));

        SqlInjectionTest sqlInjectionTest = new SqlInjectionTest();
        testsResults.add(resultService.saveService(sqlInjectionTest.testSqlInjection(url, request, params)));

        CommandInjectionTest commandInjectionTest = new CommandInjectionTest();
        testsResults.add(resultService.saveService(commandInjectionTest.testCommandInjection(url, request, params)));

        XssTest xssTest = new XssTest();
        testsResults.add(resultService.saveService(xssTest.testXss(url, request, params)));

        DataValidationTest dataValidationTest = new DataValidationTest();
        testsResults.add(resultService.saveService(dataValidationTest.testDataValidation(url, request, params)));

        Requisition req = requisitionService.saveService(new Requisition(params, REQ_TYPE.GET, Calendar.getInstance(), testsResults));

        return ResponseEntity.status(HttpStatus.OK).body(req);
    }
}
