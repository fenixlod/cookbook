package com.lunix.cookbook.utility;

import java.util.Set;
import java.util.stream.Collectors;

import com.lunix.cookbook.dto.IngredientDto;
import com.lunix.cookbook.dto.RecipeDto;
import com.lunix.cookbook.entity.Ingredient;
import com.lunix.cookbook.entity.Product;
import com.lunix.cookbook.entity.Recipe;
import com.lunix.cookbook.entity.Tag;

public class RecipeConverter {
	public static Recipe toEntity(RecipeDto dto) {
		Recipe entity = new Recipe();
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setPreparation(dto.getPreparation());
		Set<Tag> tags = dto.getTags().stream().map(t -> {
			Tag tag = new Tag();
			tag.setValue(t);
			return tag;
		}).collect(Collectors.toSet());
		entity.setTags(tags);
		Set<Ingredient> ingredients = dto.getIngredients().stream().map(i -> {
			Ingredient ingredient = new Ingredient();
			ingredient.setAmount(i.getAmount());
			ingredient.setUnit(i.getUnit());
			Product product = new Product();
			product.setName(i.getName());
			ingredient.setProduct(product);
			return ingredient;
		}).collect(Collectors.toSet());
		entity.setIngredients(ingredients);
		entity.setIdentifier(dto.getId());
		return entity;
	}

	public static RecipeDto toDto(Recipe entity) {
		RecipeDto dto = new RecipeDto();
		dto.setDescription(entity.getDescription());
		dto.setId(entity.getIdentifier());
		dto.setName(entity.getName());
		dto.setPreparation(entity.getPreparation());
		dto.setTags(entity.getTags().stream().map(t -> t.getValue()).collect(Collectors.toList()));
		dto.setIngredients(entity.getIngredients().stream().map(i -> {
			IngredientDto ingredient = new IngredientDto();
			ingredient.setAmount(i.getAmount());
			ingredient.setUnit(i.getUnit());
			ingredient.setName(i.getProduct().getName());
			return ingredient;
		}).collect(Collectors.toList()));
		return dto;
	}
}
