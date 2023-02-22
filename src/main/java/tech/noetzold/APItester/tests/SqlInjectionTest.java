package tech.noetzold.APItester.tests;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tech.noetzold.APItester.model.Result;
import tech.noetzold.APItester.util.TEST_TYPE;

import java.util.Map;

public class SqlInjectionTest extends BaseTest {

    public Result testGetSqlInjection(String url, RequestSpecification request, Map<String,String> params) {
        if(params == null) return null;
        String payload = "' or 1=1 --";
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
            return fail(TEST_TYPE.SQL_INJECTION,"SQL injection vulnerability found in parameter " + params.toString() + " with payload " + payload);
        }
        if (statusCode >= 500) {
            return fail(TEST_TYPE.SQL_INJECTION,"Server error: " + statusCode);
        }

        return success(TEST_TYPE.SQL_INJECTION);
    }
}
