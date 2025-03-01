package com.shivprakash.mentalhealthbackend.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class MlService {

    private final String ML_SERVICE_URL = "http://127.0.0.1:5000/predict"; // Adjust the URL as needed

    public Map<String, String> analyzeResponses(Map<String, Object> responses) throws Exception {
        // Create a RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Set headers for the request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Prepare the HTTP request entity
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(responses, headers);

        try {
            // Make a POST request to the ML service
            ResponseEntity<Map> responseEntity = restTemplate.postForEntity(ML_SERVICE_URL, entity, Map.class);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                // Get the response body as a Map
                Map<String, String> responseBody = (Map<String, String>) responseEntity.getBody();
                return responseBody;
            } else {
                throw new Exception("Received non-success status code from ML service: " + responseEntity.getStatusCode());
            }
        } catch (Exception e) {
            // Handle exceptions such as connection errors
            throw new Exception("Failed to get a valid response from the ML service.", e);
        }
    }
}