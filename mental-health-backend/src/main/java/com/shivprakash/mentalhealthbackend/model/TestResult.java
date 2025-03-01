package com.shivprakash.mentalhealthbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "test_results")
public class TestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relationship with User
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    private String anxietyLevel;

    @NotNull
    private String depressionLevel;

    @NotNull
    private String stressLevel;

    @NotNull
    private String generalMentalHealthScore;

    // Constructors

    public TestResult() {
    }

    public TestResult(User user, String anxietyLevel, String depressionLevel, String stressLevel, String generalMentalHealthScore) {
        this.user = user;
        this.anxietyLevel = anxietyLevel;
        this.depressionLevel = depressionLevel;
        this.stressLevel = stressLevel;
        this.generalMentalHealthScore = generalMentalHealthScore;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAnxietyLevel() {
        return anxietyLevel;
    }

    public void setAnxietyLevel(String anxietyLevel) {
        this.anxietyLevel = anxietyLevel;
    }

    public String getDepressionLevel() {
        return depressionLevel;
    }

    public void setDepressionLevel(String depressionLevel) {
        this.depressionLevel = depressionLevel;
    }

    public String getStressLevel() {
        return stressLevel;
    }

    public void setStressLevel(String stressLevel) {
        this.stressLevel = stressLevel;
    }

    public String getGeneralMentalHealthScore() {
        return generalMentalHealthScore;
    }

    public void setGeneralMentalHealthScore(String generalMentalHealthScore) {
        this.generalMentalHealthScore = generalMentalHealthScore;
    }
}