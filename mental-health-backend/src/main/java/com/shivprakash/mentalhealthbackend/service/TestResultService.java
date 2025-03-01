package com.shivprakash.mentalhealthbackend.service;

import com.shivprakash.mentalhealthbackend.model.TestResult;
import com.shivprakash.mentalhealthbackend.model.User;
import com.shivprakash.mentalhealthbackend.repository.TestResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TestResultService {

    @Autowired
    private TestResultRepository testResultRepository;

    public TestResult saveTestResult(TestResult testResult) {
        return testResultRepository.save(testResult);
    }

    public Optional<TestResult> getTestResultByUser(User user) {
        return testResultRepository.findByUser(user);
    }
    public void deleteTestResultByUser(User user) {

        Optional<TestResult> optionalTestResult = testResultRepository.findByUser(user);

        optionalTestResult.ifPresent(testResultRepository::delete);

    }
}