package com.lunix.cookbook.web;

import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import com.lunix.cookbook.object.ResponseMessage;

@ControllerAdvice
public class ExceptionHandlerAdvice {

	@ResponseBody
	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<?> executionError(ResponseStatusException ex) {
		return new ResponseEntity<ResponseMessage>(new ResponseMessage(ex.getReason()), ex.getStatus());
	}
	
	@ResponseBody
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseMessage validationError(MethodArgumentNotValidException ex) {
		return new ResponseMessage(ex.getBindingResult()
				.getAllErrors()
				.stream()
				.map(error -> error.getDefaultMessage())
				.collect(Collectors.joining(", ")));
	}

	@ResponseBody
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseMessage validationError(ConstraintViolationException ex) {
		return new ResponseMessage(ex.getConstraintViolations()
				.stream()
				.map(error -> error.getMessage())
				.collect(Collectors.joining(", ")));
	}

	@ResponseBody
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseMessage generalError(Exception ex) {
		return new ResponseMessage("Заявката не може да бъде обработена");
	}
}
