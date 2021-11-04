package com.lunix.cookbook.exception;

public class RecipeValidationException extends RuntimeException {
	private static final long serialVersionUID = -2764802446207763339L;

	public RecipeValidationException(String message) {
		super(message);
	}
}
