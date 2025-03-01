package com.shivprakash.mentalhealthbackend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import java.util.Map;

@Document(collection = "detailed_responses")
public class DetailedResponse {

    @Id
    private String id;

    @NotNull
    private Long userId;

    @NotNull
    private Map<String, Object> responses;

    // Constructors

    public DetailedResponse() {
    }

    public DetailedResponse(Long userId, Map<String, Object> responses) {
        this.userId = userId;
        this.responses = responses;
    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Map<String, Object> getResponses() {
        return responses;
    }

    public void setResponses(Map<String, Object> responses) {
        this.responses = responses;
    }
}