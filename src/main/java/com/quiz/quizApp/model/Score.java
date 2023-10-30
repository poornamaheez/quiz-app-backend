package com.quiz.quizApp.model;

import javax.persistence.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "score")
public class Score {
	@Id
	@Column
	private Long questionId;
	@Column
	private String choiceTaken;
	@Column
	private Integer score;
	public Score(Long questionId, String choiceTaken, Integer score) {
		super();
		this.questionId = questionId;
		this.choiceTaken = choiceTaken;
		this.score = score;
	}
	public Score() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}
	public String getChoiceTaken() {
		return choiceTaken;
	}
	public void setChoiceTaken(String choiceTaken) {
		this.choiceTaken = choiceTaken;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	@Override
	public String toString() {
		return "Score [questionId=" + questionId + ", choiceTaken=" + choiceTaken + ", score=" + score + "]";
	}
	
	
}
