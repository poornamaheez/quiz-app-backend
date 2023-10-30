package com.quiz.quizApp.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "result")
public class Result {
	
	@Id
	@Column
	private String username;
	@Column
	private Integer resulr;
	public Result(String username, Integer resulr) {
		super();
		this.username = username;
		this.resulr = resulr;
	}
	public Result() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getResulr() {
		return resulr;
	}
	public void setResulr(Integer resulr) {
		this.resulr = resulr;
	}
	@Override
	public String toString() {
		return "Result [username=" + username + ", resulr=" + resulr + "]";
	}
	
	

}
