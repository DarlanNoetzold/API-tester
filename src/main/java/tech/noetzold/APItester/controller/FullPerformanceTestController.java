package tech.noetzold.APItester.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.noetzold.APItester.model.FullPerformanceTest;
import tech.noetzold.APItester.service.FullPerformanceTestService;
import tech.noetzold.APItester.service.ResultService;
import tech.noetzold.APItester.service.TestGetRequisitionService;
import tech.noetzold.APItester.service.UserService;
import tech.noetzold.APItester.util.QueryStringParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/performance")
public class FullPerformanceTestController {

    @Autowired
    FullPerformanceTestService fullPerformanceTestService;

    @Autowired
    TestGetRequisitionService testGetRequisitionService;

    @Autowired
    ResultService resultService;

    @Autowired
    UserService userService;

    @GetMapping("/getByUser/{login}")
    public ResponseEntity<Page<FullPerformanceTest>> getAllByUser(HttpServletRequest request, HttpServletResponse response, Pageable pageable, @PathVariable("login") String login) {
        return new ResponseEntity<>(fullPerformanceTestService.findByUser(pageable, login), HttpStatus.OK);
    }

    @DeleteMapping("remove/{id}")
    public void remove(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") Integer id) {
        fullPerformanceTestService.deleteFullPerformanceById(id);
    }

    @PostMapping("/test")
    public ResponseEntity<String> testPerformanceEndpoint(@RequestBody FullPerformanceTest fullPerformanceTest) {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<Map<String, Object>> typeRef = new TypeReference<>() {};
        try {
            Map<String, Object> body = objectMapper.readValue(fullPerformanceTest.getBody(), typeRef);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Map<String, String> headers = QueryStringParser.parseQueryString(fullPerformanceTest.getHeaders());


        RequestSpecification requestSpecification = RestAssured.given()
                .baseUri(fullPerformanceTest.getUrl())
                .headers(headers)
                .log().all();

        long startTime = System.currentTimeMillis();
        Response response = requestSpecification.request(fullPerformanceTest.getMethod(), fullPerformanceTest.getBody());
        long endTime = System.currentTimeMillis();

        long tempoResposta = endTime - startTime;

        // Verifique se a resposta da outra API foi bem-sucedida (código 2xx ou 3xx)
        if (HttpStatus.valueOf(response.getStatusCode()).is2xxSuccessful() ||
                HttpStatus.valueOf(response.getStatusCode()).is3xxRedirection()) {
            // Retorne os resultados em formato de String
            return ResponseEntity.ok("Tempo de resposta da API: " + tempoResposta + "ms");
        } else {
            // Caso contrário, retorne o status code e o body da resposta
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody().asString());
        }
    }
}
