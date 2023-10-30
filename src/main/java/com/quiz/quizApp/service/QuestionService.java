package com.quiz.quizApp.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface QuestionService {

	ResponseEntity<String> uploadQuestionFile(MultipartFile questionFile);

	ResponseEntity<Map<String, Object>> getQuestion(Long questionId);

	ResponseEntity<Long> getNumberOfQuestions();

}
