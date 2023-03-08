package tech.noetzold.APItester.tests;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import tech.noetzold.APItester.model.PerformanceResult;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class PerformanceTest {
    private final String apiEndpoint;

    public PerformanceTest(String apiEndpoint) {
        this.apiEndpoint = apiEndpoint;
    }

    public List<PerformanceResult> runTests(int numTests, int numRequestsPerTest, String imageBase64) {
        List<PerformanceResult> results = new ArrayList<>();

        for (int i = 0; i < numTests; i++) {
            // Faz um teste de performance
            PerformanceResult result = runTest(numRequestsPerTest, imageBase64);
            results.add(result);
        }

        return results;
    }

    private PerformanceResult runTest(int numRequests, String imageBase64) {
        // Configura o endpoint da API a ser testada
        RestAssured.baseURI = apiEndpoint;

        // Converte a imagem em base64 para um array de bytes
        byte[] imageData = Base64.getDecoder().decode(imageBase64);

        // Faz uma requisição HTTP POST para a API com o corpo da imagem
        Response response = RestAssured.given()
                .contentType(ContentType.BINARY)
                .body(imageData)
                .when()
                .post("/images");

        // Verifica se a resposta foi bem-sucedida
        if (response.getStatusCode() != 200) {
            throw new RuntimeException("API returned status code " + response.getStatusCode());
        }

        // Calcula o tempo de resposta
        long responseTime = response.getTime();

        // Calcula a memória de requisição máxima
        int maxResponseSize = response.getBody().asByteArray().length;

        // Calcula o número de requisições por segundo
        long start = System.currentTimeMillis();
        for (int i = 0; i < numRequests; i++) {
            RestAssured.given()
                    .contentType(ContentType.BINARY)
                    .body(imageData)
                    .when()
                    .post("/images");
        }
        long end = System.currentTimeMillis();
        double requestsPerSecond = numRequests / ((end - start) / 1000.0);

        // Retorna o resultado do teste de performance
        return new PerformanceResult(responseTime, maxResponseSize, requestsPerSecond);
    }
}
