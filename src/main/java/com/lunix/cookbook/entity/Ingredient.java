package com.lunix.cookbook.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.lunix.cookbook.enums.IngredientAmountUnit;

@Entity
@Table(name = "ingredient")
public class Ingredient {
	@EmbeddedId
	private IngredientId id = new IngredientId();

	@ManyToOne
	@JoinColumn(name = "recipe_id", referencedColumnName = "id")
	private Recipe recipe;

	@ManyToOne
	@JoinColumn(name = "product_id", referencedColumnName = "id")
	private Product product;

	private float amount;

	@Enumerated(EnumType.STRING)
	private IngredientAmountUnit unit;

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

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
