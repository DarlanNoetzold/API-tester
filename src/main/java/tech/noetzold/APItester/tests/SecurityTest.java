package tech.noetzold.APItester.tests;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpHeaders;
import tech.noetzold.APItester.model.Result;
import tech.noetzold.APItester.util.TEST_TYPE;

import java.util.Base64;

import static org.hamcrest.Matchers.equalTo;
import java.util.Map;

public class SecurityTest extends BaseTest{

    public Result testGetSecureResponse(RequestSpecification request, String url) {
        String username = "user";
        String weakPassword = "password";
        String token = Base64.getEncoder().encodeToString((username + ":" + weakPassword).getBytes());

        Response response = request.header("Authorization", "Basic " + token)
                .when()
                .get(url);

        String responseBody = response.getBody().asString();
        int statusCode = response.getStatusCode();
        if (statusCode <= 300) {
            return fail(TEST_TYPE.SECURITY, "Security failed in token by weak password");
        }else if (statusCode >= 500) {
            return fail(TEST_TYPE.SECURITY,"Server error: " + statusCode);
        }
        return success(TEST_TYPE.SECURITY);

    }

    public Result testPostSecureResponse(RequestSpecification request, String url, Map<String, Object> body, HttpHeaders headers) {
        String username = "user";
        String weakPassword = "password";
        String token = Base64.getEncoder().encodeToString((username + ":" + weakPassword).getBytes());

        Response response = request.header("Authorization", "Basic " + token)
                .when()
                .post(url);

        String responseBody = response.getBody().asString();
        int statusCode = response.getStatusCode();
        if (statusCode <= 300) {
            return fail(TEST_TYPE.SECURITY, "Security failed in token by weak password");
        }else if (statusCode >= 500) {
            return fail(TEST_TYPE.SECURITY,"Server error: " + statusCode);
        }
        return success(TEST_TYPE.SECURITY);
    }
}
