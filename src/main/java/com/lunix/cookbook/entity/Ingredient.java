package com.lunix.cookbook.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lunix.cookbook.enums.IngredientAmountUnit;

@Entity
@Table(name = "ingredient")
public class Ingredient {
	@Id
	@GeneratedValue(generator = "ingredient_generator")
	private Long id;
	private String name;
	private float amount;

	@Enumerated(EnumType.STRING)
	private IngredientAmountUnit unit;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public IngredientAmountUnit getUnit() {
		return unit;
	}

	public void setUnit(IngredientAmountUnit unit) {
		this.unit = unit;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}
}
