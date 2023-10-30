package com.quiz.quizApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quiz.quizApp.model.Question;


public interface QuestionRepository extends JpaRepository<Question, Long> {

}
