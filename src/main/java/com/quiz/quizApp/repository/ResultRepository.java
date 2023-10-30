package com.quiz.quizApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quiz.quizApp.model.Result;

public interface ResultRepository extends JpaRepository<Result, String> {

}
