package tech.noetzold.APItester.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import tech.noetzold.APItester.model.*;
import tech.noetzold.APItester.util.TEST_TYPE;

import java.util.HashMap;
import java.util.Map;


public class SendToGPT3 {

    private TestPostRequisition testPostRequisition;

    private TestGetRequisition testGetRequisition;

    private TestPutRequisition testPutRequisition;

    private TestDeleteRequisition testDeleteRequisition;

    public SendToGPT3(TestPostRequisition testPostRequisition) {
        this.testPostRequisition = testPostRequisition;
    }

    public SendToGPT3(TestGetRequisition testGetRequisition) {
        this.testGetRequisition = testGetRequisition;
    }

    public SendToGPT3(TestPutRequisition testPutRequisition) {
        this.testPutRequisition = testPutRequisition;
    }

    public SendToGPT3(TestDeleteRequisition testDeleteRequisition) {
        this.testDeleteRequisition = testDeleteRequisition;
    }


    public Result doGptGetTest(){
        Map<String, String> bodyToSendRequest = new HashMap<>();

        bodyToSendRequest.put("url", this.testGetRequisition.getUrl());
        bodyToSendRequest.put("typeReq", "GET");
        bodyToSendRequest.put("params", this.testGetRequisition.getParameters());
        bodyToSendRequest.put("headers", this.testGetRequisition.getHeaders());
        bodyToSendRequest.put("apyKey", this.testGetRequisition.getGptKey());



        Response responseTestGetGPT = RestAssured.given().body(bodyToSendRequest).post("http://localhost:5000/gptTest").then()
                .extract()
                .response();

        return new Result(TEST_TYPE.GPT3, responseTestGetGPT.getBody().toString());
    }

    public Result doGptPostTest(){
        Map<String, String> bodyToSendRequest = new HashMap<>();

        bodyToSendRequest.put("url", this.testPostRequisition.getUrl());
        bodyToSendRequest.put("typeReq", "POST");
        bodyToSendRequest.put("headers", this.testPostRequisition.getHeaders());
        bodyToSendRequest.put("body", this.testPostRequisition.getBody());
        bodyToSendRequest.put("apyKey", this.testPostRequisition.getGptKey());



        Response responseTestGetGPT = RestAssured.given().body(bodyToSendRequest).post("http://localhost:5000/gptTest").then()
                .extract()
                .response();

        return new Result(TEST_TYPE.GPT3, responseTestGetGPT.getBody().toString());
    }

    public Result doGptPutTest() {
        Map<String, String> bodyToSendRequest = new HashMap<>();

        bodyToSendRequest.put("url", this.testPostRequisition.getUrl());
        bodyToSendRequest.put("typeReq", "PUT");
        bodyToSendRequest.put("headers", this.testPostRequisition.getHeaders());
        bodyToSendRequest.put("body", this.testPostRequisition.getBody());
        bodyToSendRequest.put("apyKey", this.testPostRequisition.getGptKey());



        Response responseTestGetGPT = RestAssured.given().body(bodyToSendRequest).post("http://localhost:5000/gptTest").then()
                .extract()
                .response();

        return new Result(TEST_TYPE.GPT3, responseTestGetGPT.getBody().toString());
    }

    public Result doGptDeleteTest() {
        Map<String, String> bodyToSendRequest = new HashMap<>();

        bodyToSendRequest.put("url", this.testPostRequisition.getUrl());
        bodyToSendRequest.put("typeReq", "DELETE");
        bodyToSendRequest.put("headers", this.testPostRequisition.getHeaders());
        bodyToSendRequest.put("body", this.testPostRequisition.getBody());
        bodyToSendRequest.put("apyKey", this.testPostRequisition.getGptKey());



        Response responseTestGetGPT = RestAssured.given().body(bodyToSendRequest).post("http://localhost:5000/gptTest").then()
                .extract()
                .response();

        return new Result(TEST_TYPE.GPT3, responseTestGetGPT.getBody().toString());
    }
}
