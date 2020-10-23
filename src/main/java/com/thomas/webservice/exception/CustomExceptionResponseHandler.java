package com.thomas.webservice.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomExceptionResponseHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> defaultExceptionHandler(Exception ex, WebRequest request) throws Exception {
		CustomExceptionResponse exception = new CustomExceptionResponse(getTimeStamp(), ex.getMessage(), request.getDescription(false));

		return new ResponseEntity<Object>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(SongNotFoundException.class)
	public final ResponseEntity<Object> songNotFoundExceptionHandler(Exception ex, WebRequest request) throws Exception {
		CustomExceptionResponse exception = new CustomExceptionResponse(getTimeStamp(), ex.getMessage(), request.getDescription(false));

		return new ResponseEntity<Object>(exception, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ArtistNotFoundException.class)
	public final ResponseEntity<Object> artistNotFoundExceptionHandler(Exception ex, WebRequest request) throws Exception {
		CustomExceptionResponse exception = new CustomExceptionResponse(getTimeStamp(), ex.getMessage(), request.getDescription(false));

		return new ResponseEntity<Object>(exception, HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, 
			WebRequest request) {
		CustomExceptionResponse exception = new CustomExceptionResponse(getTimeStamp(), "Validation Error", ex.getBindingResult().getFieldError().toString());

		return new ResponseEntity<Object>(exception, HttpStatus.BAD_REQUEST);

	}

	private String getTimeStamp() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.SSSSSS");
		return formatter.format(LocalDateTime.now());
	}
}
