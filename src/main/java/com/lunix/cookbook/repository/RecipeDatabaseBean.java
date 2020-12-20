package com.lunix.cookbook.repository;

import com.lunix.cookbook.model.Recipe;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RecipeDatabaseBean {
	private ObjectMapper mapper;
	private DatabaseProperties properties;

	public RecipeDatabaseBean(DatabaseProperties properties, ObjectMapper mapper) {
		this.properties = properties;
		this.mapper = mapper;
	}

	@Bean
	public LocalJsonDatabase<Recipe> createRecipeDatabase() {
		return new LocalJsonDatabase<Recipe>(properties.getRecipe(), mapper, Recipe.class);
	}
}
