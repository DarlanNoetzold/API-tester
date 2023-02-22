package tech.noetzold.APItester.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.noetzold.APItester.model.TestPostRequisition;
import tech.noetzold.APItester.service.ResultService;
import tech.noetzold.APItester.service.TestPostRequisitionService;
import tech.noetzold.APItester.tests.CommandInjectionTest;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/post")
public class TestPostRequisitionController {

    @Autowired
    TestPostRequisitionService testPostRequisitionService;

    @Autowired
    ResultService resultService;

    @PostMapping("/test")
    public ResponseEntity<TestPostRequisition> testPostRequest(@RequestBody TestPostRequisition testPostRequisition) {

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer <token>");

        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<Map<String, Object>> typeRef = new TypeReference<>() {};
        try {
            Map<String, Object> map = objectMapper.readValue(testPostRequisition.getBody(), typeRef);
            RequestSpecification request = RestAssured.given().headers(headers).body(testPostRequisition.getBody());
            CommandInjectionTest commandInjectionTest = new CommandInjectionTest();
            commandInjectionTest.testPostCommandInjection(request, testPostRequisition.getUrl());
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(testPostRequisition);
        }

        return ResponseEntity.status(HttpStatus.OK).body(testPostRequisition);
    }
}
