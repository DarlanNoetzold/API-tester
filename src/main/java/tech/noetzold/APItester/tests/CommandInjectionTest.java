package tech.noetzold.APItester.tests;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tech.noetzold.APItester.model.Result;
import tech.noetzold.APItester.util.TEST_TYPE;

import java.util.Map;

public class CommandInjectionTest extends BaseTest{

    public Result testCommandInjection(String url, RequestSpecification request, Map<String,String> params) {
        if(params == null) return null;
        String payload = "||ls";
        for (Map.Entry<String,String> pair : params.entrySet())
            pair.setValue(payload);
        Response response = request
                .params(params)
                .when()
                .get(url)
                .then()
                .extract()
                .response();

        String responseBody = response.getBody().asString();
        int statusCode = response.getStatusCode();
        if (responseBody.contains(payload)) {
            return fail(TEST_TYPE.COMMAND_INJECTION, "Command injection vulnerability found in parameter " + params.toString() + " with payload " + payload);
        }
        if (statusCode >= 500) {
            return fail(TEST_TYPE.COMMAND_INJECTION, "Server error: " + statusCode);
        }

        return success(TEST_TYPE.COMMAND_INJECTION);
    }
}