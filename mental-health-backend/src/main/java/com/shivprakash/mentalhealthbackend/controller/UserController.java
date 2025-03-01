package com.shivprakash.mentalhealthbackend.controller;

import com.shivprakash.mentalhealthbackend.dto.DeleteUserRequest;
import com.shivprakash.mentalhealthbackend.service.UserService;
import com.shivprakash.mentalhealthbackend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/delete")
    public ResponseEntity<?> deleteUserData(@Valid @RequestBody DeleteUserRequest request) {
        Optional<User> optionalUser = userService.getUserByEmail(request.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getName().equalsIgnoreCase(request.getName())) {
                try {
                    userService.deleteUserData(user);
                    return ResponseEntity.ok("Your data has been successfully deleted.");
                } catch (Exception e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("An error occurred while deleting your data.");
                }
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Name and email do not match our records.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }
}