package com.shivprakash.mentalhealthbackend.service;

import com.shivprakash.mentalhealthbackend.model.User;
import com.shivprakash.mentalhealthbackend.model.TestResult;
import com.shivprakash.mentalhealthbackend.model.DetailedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.*;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendReportEmail(User user, TestResult testResult, DetailedResponse detailedResponse) throws MessagingException {
        // Build email content
        String emailContent = buildReportContent(user, testResult, detailedResponse);

        // Create a MimeMessage
        MimeMessage message = mailSender.createMimeMessage();

        // Use MimeMessageHelper to set email properties
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(user.getEmail());
        helper.setSubject("Your Mental Health Assessment Report");
        helper.setText(emailContent, true); // Set 'true' to indicate HTML content

        // Send the email
        mailSender.send(message);
    }

    private String buildReportContent(User user, TestResult testResult, DetailedResponse detailedResponse) {
        // Build the HTML content using StringBuilder
        StringBuilder content = new StringBuilder();

        content.append("<!DOCTYPE html>");
        content.append("<html>");
        content.append("<head>");
        content.append("<meta charset=\"UTF-8\">");
        content.append("<style>");
        // Inline CSS styles
        content.append("body { font-family: Arial, sans-serif; background-color: #f6f6f6; margin: 0; padding: 0; }");
        content.append(".container { max-width: 600px; margin: 0 auto; background-color: #ffffff; padding: 20px; }");
        content.append("h1 { color: #333333; }");
        content.append("h2 { color: #555555; }");
        content.append("p { color: #666666; line-height: 1.5; }");
        content.append(".result-table { width: 100%; border-collapse: collapse; margin-bottom: 20px; }");
        content.append(".result-table th, .result-table td { border: 1px solid #dddddd; padding: 8px; text-align: left; }");
        content.append(".result-table th { background-color: #f2f2f2; }");
        content.append(".recommendations { background-color: #f9f9f9; padding: 10px; }");
        content.append(".footer { font-size: 12px; color: #999999; text-align: center; margin-top: 20px; }");
        content.append("</style>");
        content.append("</head>");
        content.append("<body>");
        content.append("<div class=\"container\">");

        // Email content
        content.append("<h1>Dear ").append(user.getName()).append(",</h1>");
        content.append("<p>Thank you for completing the Mental Health Assessment. Below are your responses and results:</p>");

        // User's Responses
        content.append("<h2>Your Responses:</h2>");
        content.append("<table class=\"result-table\">");
        content.append("<tr><th>Question</th><th>Your Response</th></tr>");
        Map<String, Object> responses = detailedResponse.getResponses();
        for (Map.Entry<String, Object> entry : responses.entrySet()) {
            content.append("<tr>");
            content.append("<td>").append(formatQuestion(entry.getKey())).append("</td>");
            content.append("<td>").append(entry.getValue()).append("</td>");
            content.append("</tr>");
        }
        content.append("</table>");

        // Test Results
        content.append("<h2>Your Results:</h2>");
        content.append("<table class=\"result-table\">");
        content.append("<tr><th>Assessment</th><th>Level</th></tr>");
        content.append("<tr><td>Anxiety Level</td><td>").append(testResult.getAnxietyLevel()).append("</td></tr>");
        content.append("<tr><td>Depression Level</td><td>").append(testResult.getDepressionLevel()).append("</td></tr>");
        content.append("<tr><td>Stress Level</td><td>").append(testResult.getStressLevel()).append("</td></tr>");
        content.append("<tr><td>Mental Health Severity Level</td><td>").append(testResult.getGeneralMentalHealthScore()).append("</td></tr>");
        content.append("</table>");

        // Personalized Recommendations
        content.append("<h2>Recommendations:</h2>");
        content.append("<div class=\"recommendations\">");
        content.append("<ul>");
        // Dynamic recommendations based on test results
        for (String recommendation : getRecommendations(testResult)) {
            content.append("<li>").append(recommendation).append("</li>");
        }
        content.append("</ul>");
        content.append("</div>");

        content.append("<p>If you have any questions or need further assistance, please don't hesitate to reach out.</p>");
        content.append("<p>Best regards,<br>Well-being Service Team</p>");

        content.append("</div>"); // Close container
        content.append("</body>");
        content.append("</html>");

        return content.toString();
    }

    private String formatQuestion(String questionKey) {
        // Convert snake_case to Sentence case
        String[] words = questionKey.split("_");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if (word.isEmpty()) continue;
            sb.append(Character.toUpperCase(word.charAt(0)))
              .append(word.substring(1))
              .append(" ");
        }
        return sb.toString().trim();
    }

    private String[] getRecommendations(TestResult testResult) {
        // Generate personalized recommendations based on test results
        List<String> recommendations = new ArrayList<>();

        // Anxiety Level Recommendations
        switch (testResult.getAnxietyLevel()) {
            case "High":
                recommendations.add("Consider professional counseling to address anxiety.");
                recommendations.add("Practice relaxation techniques such as deep breathing and meditation.");
                break;
            case "Moderate":
                recommendations.add("Incorporate stress-reducing activities into your daily routine.");
                recommendations.add("Stay connected with friends and family for support.");
                break;
            case "Low":
                recommendations.add("Continue maintaining your healthy coping strategies.");
                recommendations.add("Stay mindful of any changes in your anxiety levels.");
                break;
        }

        // Depression Level Recommendations
        switch (testResult.getDepressionLevel()) {
            case "High":
                recommendations.add("Seek professional help for depression.");
                recommendations.add("Engage in activities that bring you joy and fulfillment.");
                break;
            case "Moderate":
                recommendations.add("Stay active and exercise regularly to boost your mood.");
                recommendations.add("Maintain a balanced diet and sleep schedule.");
                break;
            case "Low":
                recommendations.add("Keep up your positive habits and stay socially connected.");
                recommendations.add("Monitor your mood and reach out if you notice changes.");
                break;
        }

        // Stress Level Recommendations
        switch (testResult.getStressLevel()) {
            case "High":
                recommendations.add("Identify stress triggers and develop coping strategies.");
                recommendations.add("Consider stress management workshops or support groups.");
                break;
            case "Moderate":
                recommendations.add("Practice time management and prioritize tasks.");
                recommendations.add("Take short breaks during the day to relax.");
                break;
            case "Low":
                recommendations.add("Maintain your healthy stress management techniques.");
                recommendations.add("Continue engaging in activities that help you relax.");
                break;
        }

        // Remove duplicate recommendations
        Set<String> uniqueRecommendations = new LinkedHashSet<>(recommendations);

        return uniqueRecommendations.toArray(new String[0]);
    }
}