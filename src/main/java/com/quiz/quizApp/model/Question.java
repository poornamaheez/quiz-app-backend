package com.quiz.quizApp.model;


import javax.persistence.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "question")
public class Question {
	
	@Id
	@Column
	private Long questionId;
	@Column
	private String question;
	@Column
	private String option_a;
	@Column
	private String option_b;
	@Column
	private String option_c;
	@Column
	private String option_d;
	@Column
	private String answer;
	public Question(Long questionId, String question, String option_a, String option_b, String option_c,
			String option_d, String answer) {
		super();
		this.questionId = questionId;
		this.question = question;
		this.option_a = option_a;
		this.option_b = option_b;
		this.option_c = option_c;
		this.option_d = option_d;
		this.answer = answer;
	}
	public Question() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getOption_a() {
		return option_a;
	}
	public void setOption_a(String option_a) {
		this.option_a = option_a;
	}
	public String getOption_b() {
		return option_b;
	}
	public void setOption_b(String option_b) {
		this.option_b = option_b;
	}
	public String getOption_c() {
		return option_c;
	}
	public void setOption_c(String option_c) {
		this.option_c = option_c;
	}
	public String getOption_d() {
		return option_d;
	}
	public void setOption_d(String option_d) {
		this.option_d = option_d;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	@Override
	public String toString() {
		return "Question [questionId=" + questionId + ", question=" + question + ", option_a=" + option_a
				+ ", option_b=" + option_b + ", option_c=" + option_c + ", option_d=" + option_d + ", answer=" + answer
				+ "]";
	}
	
	
	
}
