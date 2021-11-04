package com.lunix.cookbook.object;

import java.util.List;

import com.lunix.cookbook.model.RecipeOld;

public class MenuResult {
	private String name;
	private List<RecipeOld> suggestedRecipes;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<RecipeOld> getSuggestedRecipes() {
		return suggestedRecipes;
	}

	public void setSuggestedRecipes(List<RecipeOld> suggestedRecipes) {
		this.suggestedRecipes = suggestedRecipes;
	}
}
