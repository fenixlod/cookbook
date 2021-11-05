package com.lunix.cookbook.entity;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.lunix.cookbook.enums.IngredientAmountUnit;

@Entity
@Table(name = "ingredient")
public class Ingredient {
	@EmbeddedId
	private IngredientKey id;

	@ManyToOne(cascade = CascadeType.ALL)
	@MapsId("recipeId")
	@JoinColumn(name = "recipe_id")
	private Recipe recipe;

	@ManyToOne(cascade = CascadeType.ALL)
	@MapsId("productId")
	@JoinColumn(name = "product_id")
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

	public IngredientKey getId() {
		return id;
	}

	public void setId(IngredientKey id) {
		this.id = id;
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
