package com.lunix.cookbook.object;

public class RecipeSearchParameters {
	private Filters tags;
	private Filters ingredients;
	private Integer limit;
	private Integer offset;

	public Filters getTags() {
		return tags;
	}

	public void setTags(Filters tags) {
		this.tags = tags;
	}

	public Filters getIngredients() {
		return ingredients;
	}

	public void setIngredients(Filters ingredients) {
		this.ingredients = ingredients;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}
}
