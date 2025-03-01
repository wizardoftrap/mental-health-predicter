package com.shivprakash.mentalhealthbackend.controller;

import com.shivprakash.mentalhealthbackend.dto.AssessmentRequest;
import com.shivprakash.mentalhealthbackend.model.DetailedResponse;
import com.shivprakash.mentalhealthbackend.model.TestResult;
import com.shivprakash.mentalhealthbackend.model.User;
import com.shivprakash.mentalhealthbackend.service.DetailedResponseService;
import com.shivprakash.mentalhealthbackend.service.EmailService;
import com.shivprakash.mentalhealthbackend.service.MlService;
import com.shivprakash.mentalhealthbackend.service.TestResultService;
import com.shivprakash.mentalhealthbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/assessments")
public class AssessmentController {

    @Autowired
    private UserService userService;

    @Autowired
    private TestResultService testResultService;

    @Autowired
    private DetailedResponseService detailedResponseService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private MlService mlService; // Service to interact with the ML model

    @PostMapping("/submit")
    public ResponseEntity<?> submitAssessment(@Valid @RequestBody AssessmentRequest request) {
        try {
            // Check if the email is already used
            if (userService.getUserByEmail(request.getEmail()).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Email is already in use. Please use a different email address or delete your previous data.");
            }

            // Create a new user
            User user = userService.createUser(request.getName(), request.getEmail());

            // Save detailed responses
            DetailedResponse detailedResponse = new DetailedResponse(user.getId(), request.getResponses());
            detailedResponseService.saveDetailedResponse(detailedResponse);

            // Analyze responses and save test result
            TestResult testResult = analyzeResponsesAndGenerateResult(user, request.getResponses());
            testResultService.saveTestResult(testResult);

            // Send the report via email
            emailService.sendReportEmail(user, testResult, detailedResponse);

            return ResponseEntity.ok("Assessment submitted successfully. The report has been sent to your email.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing the assessment.");
        }
    }

    private TestResult analyzeResponsesAndGenerateResult(User user, Map<String, Object> responses) throws Exception {
        // Interact with the ML service to get the results
        Map<String, String> analysisResults = mlService.analyzeResponses(responses);

        // Create a TestResult object
        TestResult testResult = new TestResult();
        testResult.setUser(user);
        testResult.setAnxietyLevel(analysisResults.get("anxiety_level"));
        testResult.setDepressionLevel(analysisResults.get("depression_level"));
        testResult.setStressLevel(analysisResults.get("stress_level"));
        testResult.setGeneralMentalHealthScore(analysisResults.get("general_mental_health_score"));

        return testResult;
    }

    // Additional methods or classes if needed
}