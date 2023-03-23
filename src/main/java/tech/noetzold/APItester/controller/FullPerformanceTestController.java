package tech.noetzold.APItester.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tech.noetzold.APItester.model.*;
import tech.noetzold.APItester.service.FullPerformanceTestService;
import tech.noetzold.APItester.service.ResultService;
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
@RequestMapping("/performance")
public class FullPerformanceTestController {

    @Autowired
    FullPerformanceTestService fullPerformanceTestService;

    @Autowired
    ResultService resultService;

    @Autowired
    UserService userService;

    @GetMapping("/getByUser/{login}")
    public ResponseEntity<Page<FullPerformanceTest>> getAllByUser(HttpServletRequest request, HttpServletResponse response, Pageable pageable, @PathVariable("login") String login) {
        return new ResponseEntity<>(fullPerformanceTestService.findByUser(pageable, login), HttpStatus.OK);
    }

    @DeleteMapping("remove/{id}")
    public void remove(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") Integer id) {
        fullPerformanceTestService.deleteFullPerformanceById(id);
    }

    @PostMapping("/test")
    public ResponseEntity<FullPerformanceTest> testPerformanceEndpoint(@RequestBody FullPerformanceTest fullPerformanceTest) {

        fullPerformanceTest.setResult(callPerformanceTestByRequestType(fullPerformanceTest));

        fullPerformanceTest.setDate_request(Calendar.getInstance());

        fullPerformanceTest.setUser(userService.findUserByLogin(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()));

        FullPerformanceTest fullPerformanceTestResponse = fullPerformanceTestService.saveService(fullPerformanceTest);

        return ResponseEntity.status(HttpStatus.OK).body(fullPerformanceTestResponse);
    }

    @PostMapping("/test/list")
    public ResponseEntity<FullPerformanceTest> testPerformanceEndpointList(@RequestBody List<FullPerformanceTest> fullPerformanceTests) {

        ;

        return ResponseEntity.status(HttpStatus.OK).body(fullPerformanceTestResponse);
    }

    private List<Result> callPerformanceTestByRequestType(FullPerformanceTest fullPerformanceTest){
        List<Result> testsResults = new ArrayList<>();
        try {
            Map<String, String> headers = QueryStringParser.parseQueryString(fullPerformanceTest.getHeaders());

            fullPerformanceTest.setMethod(fullPerformanceTest.getMethod().toUpperCase());
            if("POST".equals(fullPerformanceTest.getMethod())){
                TestPostRequisition testPostRequisition = new TestPostRequisition();
                testPostRequisition.setBody(fullPerformanceTest.getBody());
                testPostRequisition.setUrl(fullPerformanceTest.getUrl());
                testPostRequisition.setHeaders(fullPerformanceTest.getHeaders());
                testPostRequisition.setUser(userService.findUserByLogin(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()));

                PerformanceTest performanceTest = new PerformanceTest(testPostRequisition);
                TypeReference<Map<String, Object>> typeRef = new TypeReference<>() {};
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> body = objectMapper.readValue(fullPerformanceTest.getBody(), typeRef);

                List<Result> performanceTestResults = performanceTest.runPostTests(1, fullPerformanceTest.getNumReq(), body, headers);
                for (Result result: performanceTestResults) testsResults.add(resultService.saveService(result));
            }else if("GET".equals(fullPerformanceTest.getMethod())){
                TestGetRequisition testGetRequisition = new TestGetRequisition();
                testGetRequisition.setUser(userService.findUserByLogin(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()));
                testGetRequisition.setHeaders(fullPerformanceTest.getHeaders());
                testGetRequisition.setParameters(fullPerformanceTest.getParameters());
                testGetRequisition.setUrl(fullPerformanceTest.getUrl());
                PerformanceTest performanceTest = new PerformanceTest(testGetRequisition);

                Map<String, String> parameters = QueryStringParser.parseQueryString(testGetRequisition.getParameters());

                List<Result> performanceTestResults = performanceTest.runGetTests(1, fullPerformanceTest.getNumReq(),parameters, headers);
                for (Result result: performanceTestResults) testsResults.add(resultService.saveService(result));
            }else if("PUT".equals(fullPerformanceTest.getMethod())){
                TestPutRequisition testPutRequisition = new TestPutRequisition();
                testPutRequisition.setBody(fullPerformanceTest.getBody());
                testPutRequisition.setUrl(fullPerformanceTest.getUrl());
                testPutRequisition.setHeaders(fullPerformanceTest.getHeaders());
                testPutRequisition.setUser(userService.findUserByLogin(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()));

                PerformanceTest performanceTest = new PerformanceTest(testPutRequisition);
                TypeReference<Map<String, Object>> typeRef = new TypeReference<>() {};
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> body = objectMapper.readValue(fullPerformanceTest.getBody(), typeRef);

                List<Result> performanceTestResults = performanceTest.runPutTests(1, fullPerformanceTest.getNumReq(), body, headers);
                for (Result result: performanceTestResults) testsResults.add(resultService.saveService(result));
            }else if("DELETE".equals(fullPerformanceTest.getMethod())){
                TestDeleteRequisition testDeleteRequisition = new TestDeleteRequisition();
                testDeleteRequisition.setUser(userService.findUserByLogin(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()));
                testDeleteRequisition.setHeaders(fullPerformanceTest.getHeaders());
                testDeleteRequisition.setParameters(fullPerformanceTest.getParameters());
                testDeleteRequisition.setUrl(fullPerformanceTest.getUrl());
                PerformanceTest performanceTest = new PerformanceTest(testDeleteRequisition);

                Map<String, String> parameters = QueryStringParser.parseQueryString(testDeleteRequisition.getParameters());

                List<Result> performanceTestResults = performanceTest.runGetTests(1, fullPerformanceTest.getNumReq(),parameters, headers);
                for (Result result: performanceTestResults) testsResults.add(resultService.saveService(result));
            }

            return testsResults;
        }catch (Exception e){
            e.printStackTrace();
        }

        return testsResults;
    }
}
