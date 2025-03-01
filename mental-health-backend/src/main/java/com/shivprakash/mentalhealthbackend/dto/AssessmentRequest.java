package com.shivprakash.mentalhealthbackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Map;

public class AssessmentRequest {

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotNull
    private Map<String, Object> responses;

    // Constructors

    public AssessmentRequest() {
    }

    public AssessmentRequest(String name, String email, Map<String, Object> responses) {
        this.name = name;
        this.email = email;
        this.responses = responses;
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, Object> getResponses() {
        return responses;
    }

    public void setResponses(Map<String, Object> responses) {
        this.responses = responses;
    }
}