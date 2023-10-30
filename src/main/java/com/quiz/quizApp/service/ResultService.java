package com.quiz.quizApp.service;

import org.springframework.http.ResponseEntity;

public interface ResultService {

	ResponseEntity<Long> calculate(String username);

}
