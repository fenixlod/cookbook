package com.lunix.cookbook.object;

import java.util.List;

import com.lunix.cookbook.model.Recipe;

public class MenuResult {
	private String name;
	private List<Recipe> suggestedRecipes;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Recipe> getSuggestedRecipes() {
		return suggestedRecipes;
	}

	public void setSuggestedRecipes(List<Recipe> suggestedRecipes) {
		this.suggestedRecipes = suggestedRecipes;
	}
}
