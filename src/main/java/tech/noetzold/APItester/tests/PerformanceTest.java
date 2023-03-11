package tech.noetzold.APItester.tests;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import tech.noetzold.APItester.model.*;
import tech.noetzold.APItester.util.TEST_TYPE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PerformanceTest {
    private TestPostRequisition testPostRequisition;
    private TestGetRequisition testGetRequisition;

    private TestPutRequisition testPutRequisition;

    public PerformanceTest(TestPostRequisition testPostRequisition) {
        this.testPostRequisition = testPostRequisition;
    }

    public PerformanceTest(TestGetRequisition testGetRequisition) {
        this.testGetRequisition = testGetRequisition;
    }

    public PerformanceTest(TestPutRequisition testPutRequisition) {
        this.testPutRequisition = testPutRequisition;
    }

    public List<Result> runPostTests(int numTests, int numRequestsPerTest, Map<String,Object> body, Map<String, String> headers) {
        List<Result> results = new ArrayList<>();

        for (int i = 0; i < numTests; i++) {
            Result result = runPostTest(numRequestsPerTest, body, headers);
            results.add(result);
        }

        return results;
    }

    private Result runPostTest(int numRequests, Map<String,Object> body, Map<String, String> headers) {

        Response response = RestAssured.given()
                .headers(headers)
                .body(body)
                .when()
                .post(testPostRequisition.getUrl());

        if (response.getStatusCode() < 200 || response.getStatusCode() >= 300) {
            return new Result(TEST_TYPE.PERFORMANCE, "Error " + response.getStatusCode() + " by header " + response.getHeaders() + " by body " + response.getBody());
        }

        long responseTime = response.getTime();

        int maxResponseSize = response.getBody().asByteArray().length;

        long start = System.currentTimeMillis();
        for (int i = 0; i < numRequests; i++) {
            RestAssured.given().headers(headers).body(body).when().post(testPostRequisition.getUrl());
        }
        long end = System.currentTimeMillis();
        double requestsPerSecond = numRequests / ((end - start) / 1000.0);

        return new Result(TEST_TYPE.PERFORMANCE, new PerformanceResult(responseTime, maxResponseSize, requestsPerSecond).toString());
    }

    public List<Result> runGetTests(int numTests, int numRequestsPerTest, Map<String,String> params, Map<String, String> headers) {
        List<Result> results = new ArrayList<>();

        for (int i = 0; i < numTests; i++) {
            Result result = runGetTest(numRequestsPerTest, params, headers);
            results.add(result);
        }

        return results;
    }

    private Result runGetTest(int numRequests, Map<String,String> params, Map<String, String> headers) {

        Response response = RestAssured.given()
                .headers(headers)
                .params(params)
                .when()
                .post(testGetRequisition.getUrl());

        if (response.getStatusCode() < 200 || response.getStatusCode() >= 300) {
            return new Result(TEST_TYPE.PERFORMANCE, "Error " + response.getStatusCode() + " by header " + response.getHeaders() + " by body " + response.getBody());
        }

        long responseTime = response.getTime();

        int maxResponseSize = response.getBody().asByteArray().length;

        long start = System.currentTimeMillis();
        for (int i = 0; i < numRequests; i++) {
            RestAssured.given().headers(headers).params(params).when().post(testGetRequisition.getUrl());
        }
        long end = System.currentTimeMillis();
        double requestsPerSecond = numRequests / ((end - start) / 1000.0);

        return new Result(TEST_TYPE.PERFORMANCE, new PerformanceResult(responseTime, maxResponseSize, requestsPerSecond).toString());
    }

    public List<Result> runPutTests(int i, int i1, Map<String, Object> body, Map<String, String> headers) {
        return runPostTests(i, i1, body, headers);
    }
}
