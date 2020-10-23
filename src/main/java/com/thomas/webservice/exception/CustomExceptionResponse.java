package com.thomas.webservice.exception;

public class CustomExceptionResponse {

	private String timestamp;
	private String message;
	private String details;
	
	public CustomExceptionResponse(String string, 
			String message, String details) {
		super();
		this.timestamp = string;
		this.message = message;
		this.details = details;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
}
