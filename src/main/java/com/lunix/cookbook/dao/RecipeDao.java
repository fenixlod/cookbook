package com.lunix.cookbook.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.lunix.cookbook.model.Recipe;
import com.lunix.cookbook.object.Filters;
import com.lunix.cookbook.object.RecipeSearchParameters;
import com.lunix.cookbook.repository.LocalJsonDatabase;

@Repository
public class RecipeDao {
	private LocalJsonDatabase<Recipe> db;
	private Map<String, Recipe> recipes;

	public RecipeDao(LocalJsonDatabase<Recipe> database) {
		this.db = database;
	}

	public void save() throws IOException {
		db.save(recipes.values());
	}

	private Map<String, Recipe> getRecipes() throws IOException {
		if (recipes == null) {
			recipes = new HashMap<>();
			db.load().forEach(obj -> {
				recipes.put(obj.getId(), obj);
			});
		}

		return recipes;
	}

	public void insert(Recipe newRecipe) throws IOException {
		getRecipes().put(newRecipe.getId(), newRecipe);
		save();
	}

	public Optional<Recipe> getByName(String recipeName) throws IOException {
		List<Recipe> foundRecipes = getRecipes().values().stream().filter(r -> r.getName().equalsIgnoreCase(recipeName)).collect(Collectors.toList());
		if (foundRecipes.isEmpty())
			return Optional.empty();

		return Optional.of(foundRecipes.get(0));
	}

	public Optional<Recipe> getById(String recipeId) throws IOException {
		Recipe foundRecipe = getRecipes().get(recipeId);
		if (foundRecipe == null)
			return Optional.empty();

		return Optional.of(foundRecipe);
	}

	public void delete(String recipeId) throws IOException {
		getRecipes().remove(recipeId);
		save();
	}

	public List<Recipe> getRecipes(Optional<RecipeSearchParameters> filter) throws IOException {
		// TODO: Implement filtering
		Collection<Recipe> allRecipes = getRecipes().values();
		if (filter.isEmpty())
			return new ArrayList<>(allRecipes);
		else {
			RecipeSearchParameters searchParameters = filter.get();
			Filters tagFilters = searchParameters.getTags();
			List<String> includeTags = tagFilters.getIncludes() == null ? Collections.emptyList() : tagFilters.getIncludes();
			List<String> excludeTags = tagFilters.getExcludes() == null ? Collections.emptyList() : tagFilters.getExcludes();

			allRecipes = allRecipes.stream().filter(recipe -> recipe.getTags().containsAll(includeTags))
					.filter(recipe -> Collections.disjoint(recipe.getTags(), excludeTags)).collect(Collectors.toList());

			return new ArrayList<>(allRecipes);
		}
	}

	public void update(String id, Recipe recipe) throws IOException {
		getRecipes().put(id, recipe);
		save();
	}

}
