package tech.noetzold.APItester.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.noetzold.APItester.model.TestPostRequisition;
import tech.noetzold.APItester.service.ResultService;
import tech.noetzold.APItester.service.TestPostRequisitionService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/post")
public class TestPostRequisitionController {

    @Autowired
    TestPostRequisitionService testPostRequisitionService;

    @Autowired
    ResultService resultService;

    @PostMapping("/post-request")
    public ResponseEntity<String> testPostRequest(@RequestBody TestPostRequisition testPostRequisition) {

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer <token>");


    }
}
