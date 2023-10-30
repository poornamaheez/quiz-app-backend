package com.quiz.quizApp.service.impl;

import java.util.List;

import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.quiz.quizApp.model.Result;
import com.quiz.quizApp.model.Score;
import com.quiz.quizApp.repository.ResultRepository;
import com.quiz.quizApp.repository.ScoreRepository;
import com.quiz.quizApp.service.ResultService;


@Service
public class ResultServiceImpl implements ResultService {
	
	@Autowired
	ScoreRepository scoreRepository;
	
	@Autowired
	ResultRepository resultRepository;
	
	@Override
	public ResponseEntity<Long> calculate(String username){
		Result result = new Result();
		
		Long score = scoreRepository.totalScore();
		
		result.setUsername(username);
		result.setResulr(Integer.parseInt(score!=null?score.toString():"0"));
		
		
		resultRepository.deleteById(username);
		
		resultRepository.save(result);
		return ResponseEntity.status(HttpStatus.OK).body(score!=null?score:0);
	}
}
