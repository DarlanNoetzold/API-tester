package tech.noetzold.APItester.tests;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tech.noetzold.APItester.model.PerformanceResult;
import tech.noetzold.APItester.model.Result;
import tech.noetzold.APItester.model.TestPostRequisition;
import tech.noetzold.APItester.util.TEST_TYPE;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class PerformanceTest {
    private final TestPostRequisition testPostRequisition;

    public PerformanceTest(TestPostRequisition testPostRequisition) {
        this.testPostRequisition = testPostRequisition;
    }

    public List<Result> runTests(int numTests, int numRequestsPerTest, RequestSpecification request, Map<String,Object> body, Map<String, String> headers) {
        List<Result> results = new ArrayList<>();

        for (int i = 0; i < numTests; i++) {
            Result result = runTest(numRequestsPerTest, request, body, headers);
            results.add(result);
        }

        return results;
    }

    private Result runTest(int numRequests, RequestSpecification request, Map<String,Object> body, Map<String, String> headers) {

        Response response = request
                .headers(headers)
                .body(body)
                .when()
                .post(testPostRequisition.getUrl());

        if (response.getStatusCode() != 200) {
            throw new RuntimeException("API returned status code " + response.getStatusCode());
        }

        long responseTime = response.getTime();

        int maxResponseSize = response.getBody().asByteArray().length;

        long start = System.currentTimeMillis();
        for (int i = 0; i < numRequests; i++) {
            request.headers(headers).body(body).when().post(testPostRequisition.getUrl());
        }
        long end = System.currentTimeMillis();
        double requestsPerSecond = numRequests / ((end - start) / 1000.0);

        return new Result(TEST_TYPE.PERFORMANCE, new PerformanceResult(responseTime, maxResponseSize, requestsPerSecond).toString());
    }
}
