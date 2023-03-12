package tech.noetzold.APItester.tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.web.util.UriComponentsBuilder;
import tech.noetzold.APItester.model.Result;
import tech.noetzold.APItester.util.TEST_TYPE;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class SqlInjectionTest extends BaseTest {

    public Result testGetSqlInjection(String url, RequestSpecification request, Map<String,String> params) {
        if (params == null) return null;
        String payload = "' or 1=1 --";
        try {
            payload = URLEncoder.encode(payload, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);
        for (Map.Entry<String, String> pair : params.entrySet()) {
            uriBuilder.queryParam(pair.getKey(), payload);
        }

        Response response = request
                .get(uriBuilder.toUriString())
                .then()
                .extract()
                .response();

        String responseBody = response.getBody().asString();
        int statusCode = response.getStatusCode();
        if (responseBody.contains(payload)) {
            return fail(TEST_TYPE.SQL_INJECTION, "SQL injection vulnerability found in parameter " + params.toString() + " with payload " + payload);
        }
        if (statusCode >= 500) {
            return fail(TEST_TYPE.SQL_INJECTION, "Server error: " + statusCode);
        }

        return success(TEST_TYPE.SQL_INJECTION);
    }

    public Result testPostSqlInjection(RequestSpecification request, String url, Map<String, Object> body, Map<String, String> headers) {
        if (body == null) return null;
        String payload = "' or 1=1 --";

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);

        for (Map.Entry<String, Object> pair : body.entrySet()) {
            if (pair.getValue() instanceof String) {
                String encodedValue = URLEncoder.encode(pair.getValue().toString(), StandardCharsets.UTF_8);
                uriBuilder.queryParam(pair.getKey(), encodedValue);
            }
        }

        String requestBody = uriBuilder.build().getQuery();

        Response response = request.body(requestBody)
                .contentType(ContentType.URLENC)
                .headers(headers)
                .when()
                .post(url)
                .then()
                .extract()
                .response();

        String responseBody = response.getBody().asString();
        int statusCode = response.getStatusCode();
        if (responseBody.contains(payload)) {
            return fail(TEST_TYPE.SQL_INJECTION, "SQL injection vulnerability found in parameter " + body.toString() + " with payload " + payload);
        }
        if (statusCode >= 500) {
            return fail(TEST_TYPE.SQL_INJECTION, "Server error: " + statusCode);
        }

        return success(TEST_TYPE.SQL_INJECTION);
    }


    public Result testPutSqlInjection(RequestSpecification request, String url, Map<String, Object> body, Map<String, String> headers) {
        return testPostSqlInjection(request, url, body, headers);
    }

    public Result testDeleteSqlInjection(String url, RequestSpecification request, Map<String, String> params) {
    }
}
