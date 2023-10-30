package com.quiz.quizApp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.quiz.quizApp.exception.ResourceNotFoundException;
import com.quiz.quizApp.helper.QuestionHelper;
import com.quiz.quizApp.model.Question;
import com.quiz.quizApp.repository.QuestionRepository;
import com.quiz.quizApp.service.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService {
	
	@Autowired
	QuestionRepository questionRepository;
	
	@Override
	public ResponseEntity<String> uploadQuestionFile(MultipartFile questionFile) {
		System.out.println("upload");
        Map<String, String> errors = new HashMap<>();

		if(!QuestionHelper.hasExcelFormat(questionFile)) {
            errors.put("formatError", "Please upload an excel file for " + questionFile.getOriginalFilename()+ "data!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.toString());
		}
		try {
			List<Question> questions =  QuestionHelper.excelToQuestions(questionFile.getInputStream() , questionFile);
			questionRepository.saveAll(questions);
		} catch (Exception e) {
            errors.put("UploadError",e.getMessage());
		}finally {
			if(!errors.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.toString().replace("=", " : ").replaceAll("[{}]", "").replaceFirst("\\[", "").replaceFirst("]$", ""));
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body("Files Uploaded Successfully");

		
	}
	
	@Override
	public ResponseEntity<Map<String, Object>> getQuestion(Long questionId){
		Optional<Question> optionalQuestion = questionRepository.findById(questionId);
		if (optionalQuestion.isPresent()) {
			Question question = optionalQuestion.get();
			Map<String, Object> response = new HashMap<>();
			response.put("Question_number", question.getQuestionId());
			response.put("Question", question.getQuestion());
			response.put("op_a", question.getOption_a());
			response.put("op_b", question.getOption_b());
			response.put("op_c", question.getOption_c());
			response.put("op_d", question.getOption_d());
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}else {
	          throw new ResourceNotFoundException("Question not found with id: " + questionId);
		}
	}
	
	@Override
	public ResponseEntity<Long> getNumberOfQuestions(){
		Long number = questionRepository.count();
		System.out.println(number);
		return ResponseEntity.status(HttpStatus.OK).body(number);
	}
}
