package tech.noetzold.APItester.tests;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tech.noetzold.APItester.model.Result;

public class SecurityTest {

    public Result testGetSecureResponse(RequestSpecification request, String url) {
        Response response = request.with().get(url);
        return new Result();
    }
}
