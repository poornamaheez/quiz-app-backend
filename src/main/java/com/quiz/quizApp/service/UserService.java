package com.quiz.quizApp.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.quiz.quizApp.model.User;

public interface UserService {
    List<User> getAllUsers();

    Optional<User> getUserById(Long userId);

    User getUserByUsername(String usernamesString);

    ResponseEntity<String> createUser(Map<String, String> user) throws NoSuchAlgorithmException ;

    User updateUser(User user);

    void deleteUserById(Long userId);

	ResponseEntity<String> loginUser(Map<String, String> user) throws NoSuchAlgorithmException;


}
