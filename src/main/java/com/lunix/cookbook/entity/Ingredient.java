package com.lunix.cookbook.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.lunix.cookbook.enums.IngredientAmountUnit;

@Entity
@Table(name = "ingredient")
public class Ingredient {
	@Id
	@GeneratedValue(generator = "ingredient_generator")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "recipe_id")
	private Recipe recipe;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH })
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
