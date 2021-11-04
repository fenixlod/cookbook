package com.lunix.cookbook.enums;

public enum IngredientAmountUnit {
	COUNT("COUNT"), VOLUME("VOLUME"), WEIGHT("WEIGHT"), SOUP_SPOON("SOUP_SPOON"), COFFE_SPOON("COFFE_SPOON"), TEA_SPOON("TEA_SPOON");

	private String value;

	private IngredientAmountUnit(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
