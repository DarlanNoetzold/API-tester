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
import tech.noetzold.APItester.model.User;
import tech.noetzold.APItester.service.TestGetRequisitionService;
import tech.noetzold.APItester.service.ResultService;
import tech.noetzold.APItester.service.UserService;
import tech.noetzold.APItester.tests.*;

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
            @RequestBody Map<String, Object> requestBody) {
        String url = (String) requestBody.get("url");
        Map<String, String> headers = (Map<String, String>) requestBody.get("headers");
        Map<String, String> params = (Map<String, String>) requestBody.get("params");

        RequestSpecification request = RestAssured.given()
                .urlEncodingEnabled(false);

        if (headers != null && !headers.isEmpty()) {
            request.headers(headers);
        }

        if (params != null && !params.isEmpty()) {
            request.params(params);
        }

        List<Result> testsResults = callTestsAndReturnResults(request,url,params);

        User user = userService.findUserByLogin(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

        TestGetRequisition req = testGetRequisitionService.saveService(new TestGetRequisition(params, Calendar.getInstance(), testsResults, user));

        return ResponseEntity.status(HttpStatus.OK).body(req);
    }

    private List<Result> callTestsAndReturnResults(RequestSpecification request, String url, Map<String, String> params){
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

        return testsResults;

    }
}
