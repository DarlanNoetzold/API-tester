package tech.noetzold.APItester.controller;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.noetzold.APItester.model.CompleteRequest;
import tech.noetzold.APItester.service.CompleteRequestService;
import tech.noetzold.APItester.service.ResultService;
import tech.noetzold.APItester.service.UserService;
import tech.noetzold.APItester.util.QueryStringParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/automated")
public class AutomatedRequestsController {

    @Autowired
    CompleteRequestService completeRequestService;

    @Autowired
    ResultService resultService;

    @Autowired
    UserService userService;

    @PostMapping("/test/list")
    public void testPerformanceEndpointList(@RequestBody List<CompleteRequest> fullPerformanceTests) {
        Map<String, Object> variableMap = new HashMap<>();

        for (CompleteRequest fullPerformanceTest : fullPerformanceTests) {
            String requestBody = replaceVariables(fullPerformanceTest.getBody(), variableMap);
            String requestHeaders = replaceVariables(fullPerformanceTest.getHeaders(), variableMap);
            Map<String, String> headers = QueryStringParser.parseQueryString(requestHeaders);

            String requestUrl = replaceVariables(fullPerformanceTest.getUrl(), variableMap);

            RequestSpecification requestSpec = RestAssured.given()
                    .headers(headers)
                    .body(requestBody);

            Response response;
            String method = fullPerformanceTest.getMethod();
            if (method.equalsIgnoreCase("get")) {
                response = requestSpec.when().get(requestUrl);
            } else if (method.equalsIgnoreCase("post")) {
                response = requestSpec.when().post(requestUrl);
            } else if (method.equalsIgnoreCase("put")) {
                response = requestSpec.when().put(requestUrl);
            } else if (method.equalsIgnoreCase("delete")) {
                response = requestSpec.when().delete(requestUrl);
            }else{
                response = requestSpec.when().post(requestUrl);
            }

            String responseBody = response.getBody().asString();
            try {
                variableMap.putAll(new JsonPath(responseBody).getMap(""));
            }catch (Exception exception){
                variableMap.put("token", responseBody);
            }
        }
    }

    private static <T> T replaceVariables(T input, Map<String, Object> variableMap) {
        if (input == null) {
            return null;
        }
        String str = input.toString();
        if (str.isEmpty()) {
            return input;
        }

        Pattern pattern = Pattern.compile("\\{\\{([^}]+)\\}\\}");
        Matcher matcher = pattern.matcher(str);

        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String varName = matcher.group(1);
            Object varValue = variableMap.get(varName);
            String replacement = (varValue != null) ? varValue.toString() : "";
            matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(sb);
        return (T) sb.toString();
    }
}
