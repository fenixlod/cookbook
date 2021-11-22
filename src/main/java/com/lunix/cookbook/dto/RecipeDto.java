package com.lunix.cookbook.dto;

import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import com.lunix.cookbook.utility.UniqueProducts;

public class RecipeDto {
	private String id;

	@NotBlank(message = "Име на рецептата е задължително поле")
	private String name;
	private String description;
	private String preparation;
	private Set<String> tags;

	@UniqueProducts(message = "Рецептата съдържа дублиращи се продукти")
	private List<IngredientDto> ingredients;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPreparation() {
		return preparation;
	}

	public void setPreparation(String preparation) {
		this.preparation = preparation;
	}

	public Set<String> getTags() {
		return tags;
	}

	public void setTags(Set<String> tags) {
		this.tags = tags;
	}

	public List<IngredientDto> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<IngredientDto> ingredients) {
		this.ingredients = ingredients;
	}
}
