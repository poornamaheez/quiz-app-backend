package com.quiz.quizApp.model;

import javax.persistence.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class User {
	@Id
	@Column
	private String username;
	
	@Column
	private String passwordString;
	
	@Column
	private String salt;

	

	public User(String username) {
		super();
		this.username = username;
	}



	public User(String username, String passwordString, String salt) {
		super();
		this.username = username;
		this.passwordString = passwordString;
		this.salt = salt;
	}



	public User() {
		super();
		// TODO Auto-generated constructor stub
	}




	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getPasswordString() {
		return passwordString;
	}



	public void setPasswordString(String passwordString) {
		this.passwordString = passwordString;
	}



	public String getSalt() {
		return salt;
	}



	public void setSalt(String salt) {
		this.salt = salt;
	}



	@Override
	public String toString() {
		return "User [username=" + username + ", passwordString=" + passwordString + ", salt="
				+ salt + "]";
	}

	
}
