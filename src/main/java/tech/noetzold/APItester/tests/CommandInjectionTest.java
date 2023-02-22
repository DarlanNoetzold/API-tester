package tech.noetzold.APItester.tests;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tech.noetzold.APItester.model.Result;
import tech.noetzold.APItester.util.TEST_TYPE;

import java.util.Map;

public class CommandInjectionTest extends BaseTest{

    public Result testGetCommandInjection(String url, RequestSpecification request, Map<String,String> params) {
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

    public void testPostCommandInjection(RequestSpecification request) {
        Response response = request.param("name", "&& echo 'Hello World' &&").post("/endpoint");
        int statusCode = response.getStatusCode();

        if (statusCode == 200) {
            System.out.println("Test failed! Command Injection may be possible.");
        } else {
            System.out.println("Test passed! Command Injection is not possible.");
        }
    }
}
