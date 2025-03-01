package com.shivprakash.mentalhealthbackend.service;

import com.shivprakash.mentalhealthbackend.model.User;
import com.shivprakash.mentalhealthbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestResultService testResultService;
    @Autowired
    private DetailedResponseService detailedResponseService;
    public User createUser(String name, String email) throws Exception {
        // Check if email is already in use
        if (userRepository.findByEmail(email).isPresent()) {
            throw new Exception("Email is already in use.");
        }

        // Create and save the new user
        User user = new User(name, email);
        return userRepository.save(user);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public void deleteUserData(User user) {

        // Delete TestResult

        testResultService.deleteTestResultByUser(user);


        // Delete DetailedResponse

        detailedResponseService.deleteDetailedResponseByUserId(user.getId());


        // Delete User

        userRepository.delete(user);

    }
}