package com.thomas.webservice.sample;

public class SampleBean {
	
	String message;

	public SampleBean(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "SampleBean [message=" + message + "]";
	} 
}