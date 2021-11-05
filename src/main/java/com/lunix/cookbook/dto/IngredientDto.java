package com.lunix.cookbook.dto;

import com.lunix.cookbook.enums.IngredientAmountUnit;

public class IngredientDto {
	private String name;
	private float amount;
	private IngredientAmountUnit unit;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public IngredientAmountUnit getUnit() {
		return unit;
	}

	public void setUnit(IngredientAmountUnit unit) {
		this.unit = unit;
	}
}
