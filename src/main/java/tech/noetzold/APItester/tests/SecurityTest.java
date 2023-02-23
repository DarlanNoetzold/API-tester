package tech.noetzold.APItester.tests;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpHeaders;
import tech.noetzold.APItester.model.Result;

import java.util.Map;

public class SecurityTest {

    public Result testGetSecureResponse(RequestSpecification request, String url) {
        Response response = request.with().get(url);
        return new Result();
    }

    public Result testPostSecureResponse(RequestSpecification request, String url, Map<String, Object> body, HttpHeaders headers) {
        Response response = request.with().get(url);
        return new Result();
    }
}
