package com.lunix.cookbook.object;

public class MenuMeal {
	private String name;
	private MealRequirement tags;
	private MealRequirement ingredients;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MealRequirement getTags() {
		return tags;
	}

	public void setTags(MealRequirement tags) {
		this.tags = tags;
	}

	public MealRequirement getIngredients() {
		return ingredients;
	}

	public void setIngredients(MealRequirement ingredients) {
		this.ingredients = ingredients;
	}
}
