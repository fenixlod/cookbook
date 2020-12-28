package com.lunix.cookbook.object;

import java.util.Map;

public class RecipeFilterCounts {
	private Map<String, Long> tags;
	private Map<String, Long> ingredients;

	public Map<String, Long> getTags() {
		return tags;
	}

	public void setTags(Map<String, Long> tags) {
		this.tags = tags;
	}

	public Map<String, Long> getIngredients() {
		return ingredients;
	}

	public void setIngredients(Map<String, Long> ingredients) {
		this.ingredients = ingredients;
	}
}
