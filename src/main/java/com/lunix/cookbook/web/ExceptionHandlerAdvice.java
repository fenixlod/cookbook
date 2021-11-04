package com.lunix.cookbook.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.lunix.cookbook.exception.RecipeValidationException;
import com.lunix.cookbook.utility.ResponseMessage;

@ControllerAdvice
public class ExceptionHandlerAdvice {

	@ResponseBody
	@ExceptionHandler(RecipeValidationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseMessage recipeValidationError(RecipeValidationException ex) {
		return new ResponseMessage(ex.getMessage());
	}

	@ResponseBody
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseMessage generalError(Exception ex) {
		return new ResponseMessage("Заявката не може да бъде обработена");
	}
}
