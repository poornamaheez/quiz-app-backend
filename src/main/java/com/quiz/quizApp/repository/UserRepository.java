package com.quiz.quizApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quiz.quizApp.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByUsername(String usernamestString);



}
