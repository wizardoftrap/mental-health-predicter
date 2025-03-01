package com.shivprakash.mentalhealthbackend.controller;

import com.shivprakash.mentalhealthbackend.dto.ResendReportRequest;
import com.shivprakash.mentalhealthbackend.model.DetailedResponse;
import com.shivprakash.mentalhealthbackend.model.TestResult;
import com.shivprakash.mentalhealthbackend.model.User;
import com.shivprakash.mentalhealthbackend.service.DetailedResponseService;
import com.shivprakash.mentalhealthbackend.service.EmailService;
import com.shivprakash.mentalhealthbackend.service.TestResultService;
import com.shivprakash.mentalhealthbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private UserService userService;

    @Autowired
    private TestResultService testResultService;

    @Autowired
    private DetailedResponseService detailedResponseService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/resend")
    public ResponseEntity<?> resendReport(@Valid @RequestBody ResendReportRequest request) {
        Optional<User> optionalUser = userService.getUserByEmail(request.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getName().equalsIgnoreCase(request.getName())) {
                try {
                    // Fetch the test result
                    Optional<TestResult> optionalTestResult = testResultService.getTestResultByUser(user);
                    // Fetch the detailed responses
                    Optional<DetailedResponse> optionalDetailedResponse = detailedResponseService.getResponseByUserId(user.getId());

                    if (optionalTestResult.isPresent() && optionalDetailedResponse.isPresent()) {
                        TestResult testResult = optionalTestResult.get();
                        DetailedResponse detailedResponse = optionalDetailedResponse.get();
                        // Email the report with responses
                        emailService.sendReportEmail(user, testResult, detailedResponse);
                        return ResponseEntity.ok("Your report has been re-sent to your email.");
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No test result or responses found for the user.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("An error occurred while sending the report.");
                }
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Name and email do not match our records.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found.");
        }
    }
}