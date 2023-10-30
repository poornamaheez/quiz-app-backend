package com.quiz.quizApp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.quiz.quizApp.model.Question;
import com.quiz.quizApp.model.Score;
import com.quiz.quizApp.repository.QuestionRepository;
import com.quiz.quizApp.repository.ScoreRepository;
import com.quiz.quizApp.service.ScoreService;

@Service
public class ScoreServiceImpl implements ScoreService {
	@Autowired
	QuestionRepository questionRepository;
	
	@Autowired
	ScoreRepository scoreRepository;
	
	@Override
	public ResponseEntity<String> recordOption(Long questionId, String Option){
		
		Optional<Question> optionalQuestion = questionRepository.findById(questionId);
		
		Score score = new Score();
		if(optionalQuestion.isPresent()) {
			Question qn = optionalQuestion.get();
			
			String answer = qn.getAnswer();
			
			if (answer.equalsIgnoreCase(Option)) {
				score.setScore(1);
			}
			else {
				score.setScore(0);
			}
			
			score.setChoiceTaken(Option);
			score.setQuestionId(questionId);
			
			scoreRepository.save(score);
			
			return ResponseEntity.status(HttpStatus.OK).body("Your response has been recorded");
		}
		System.out.println(questionId+"    "+Option);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
	}
	
	@Override
	public ResponseEntity<Map<String, List<String>>> evaluate(){
		List<Long> wrongQids = scoreRepository.getQIds();
		List<String> wrongOptions = scoreRepository.getOptionTook();
		System.out.println(wrongQids.toString());
		System.out.println(wrongOptions.toString());
		Map<String, List<String>> returingjson = new HashMap<>();
		List<Question> optionalQuestion = questionRepository.findAllById(wrongQids);
		
		returingjson.put("Question Numbers", wrongQids.stream().map(String::valueOf).collect(Collectors.toList()));
		System.out.println(returingjson.toString());
		List<String> qns = new ArrayList<>();
		List<String> crct = new ArrayList<>();
		List<String> wrng = new ArrayList<>();
		for(int i=0;i<wrongQids.size();i++) {
			qns.add(optionalQuestion.get(i).getQuestion());
			switch (optionalQuestion.get(i).getAnswer()) {
			case "A": {
				crct.add(optionalQuestion.get(i).getOption_a());
				break;
			}
			case "B": {
				crct.add(optionalQuestion.get(i).getOption_b());
				break;
			}
			case "C": {
				crct.add(optionalQuestion.get(i).getOption_c());
				break;
			}
			case "D": {
				crct.add(optionalQuestion.get(i).getOption_d());
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value in Questions: " + optionalQuestion);
			}
			
			if(wrongOptions.get(i).equalsIgnoreCase("blank")) {
				wrng.add("Not Answered");
			}
			else {
				switch (wrongOptions.get(i)) {
				case "A": {
					wrng.add(optionalQuestion.get(i).getOption_a());
					break;
				}
				case "B": {
					wrng.add(optionalQuestion.get(i).getOption_b());
					break;
				}
				case "C": {
					wrng.add(optionalQuestion.get(i).getOption_c());
					break;
				}
				case "D": {
					wrng.add(optionalQuestion.get(i).getOption_d());
					break;
				}
				default:
					throw new IllegalArgumentException("Unexpected value in chosen answer: " + wrongOptions);
				}
			}
		}
		
		returingjson.put("Question", qns);
		returingjson.put("Correct Option", crct);
		returingjson.put("Option Chosen", wrng);
		
		
		System.out.println(returingjson.toString());
		return ResponseEntity.status(HttpStatus.OK).body(returingjson);
	}
}
