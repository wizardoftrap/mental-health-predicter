package com.shivprakash.mentalhealthbackend.repository;

import com.shivprakash.mentalhealthbackend.model.DetailedResponse;
import com.shivprakash.mentalhealthbackend.model.TestResult;
import com.shivprakash.mentalhealthbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult, Long> {

    Optional<TestResult> findByUser(User user);
    Optional<DetailedResponse> findByUserId(Long userId);
}