package com.quiz.quizApp.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;


public interface ScoreService {

	ResponseEntity<String> recordOption(Long questionId, String Option);

	ResponseEntity<Map<String, List<String>>> evaluate();

}
