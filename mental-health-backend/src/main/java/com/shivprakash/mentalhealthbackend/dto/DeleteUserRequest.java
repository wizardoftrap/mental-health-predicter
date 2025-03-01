package com.shivprakash.mentalhealthbackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class DeleteUserRequest {

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    // Constructors
    public DeleteUserRequest() {
    }

    public DeleteUserRequest(String name, String email) {
        this.name = name;
        this.email = email;
    }

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



}