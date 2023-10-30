package com.quiz.quizApp.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.quiz.quizApp.model.Score;

public interface ScoreRepository extends JpaRepository<Score, Long> {
	
	
	@Query(value = "select sum(score) from quiz.score;",nativeQuery = true )
	Long totalScore();
	
	@Query(value = "select question_id from quiz.score where score=0;",nativeQuery = true)
	List<Long> getQIds();
	
	@Query(value = "select choice_taken from quiz.score where score=0;",nativeQuery = true)
	List<String> getOptionTook();
}
