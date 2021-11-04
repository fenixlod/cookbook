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
import java.util.stream.Stream;

import com.lunix.cookbook.repository.TagRepository;
import org.springframework.stereotype.Repository;

import com.lunix.cookbook.model.Recipe;
import com.lunix.cookbook.object.Filters;
import com.lunix.cookbook.object.RecipeSearchParameters;
import com.lunix.cookbook.repository.LocalJsonDatabase;
import com.lunix.cookbook.repository.RecipeRepository;

@Repository
public class RecipeDao {
	private LocalJsonDatabase<Recipe> db;
	private Map<String, Recipe> recipes;
	private final RecipeRepository recipeRepo;
	private final TagRepository tagRepo;

	public RecipeDao(LocalJsonDatabase<Recipe> database, RecipeRepository recipeRepo, TagRepository tagRepo) {
		this.db = database;
		this.recipeRepo = recipeRepo;
		this.tagRepo = tagRepo;
	}

	public void save() throws IOException {
		db.save(recipes.values());
	}

	private synchronized Map<String, Recipe> getRecipes() throws IOException {
		if (recipes == null) {
			recipes = new HashMap<>();
			db.load().forEach(obj -> {
				recipes.put(obj.getId(), obj);
			});
		}

		return recipes;
	}

	public void createNew(com.lunix.cookbook.entity.Recipe newRecipe) {
		var tags = newRecipe
				.getTags()
				.stream()
				.map(tag -> tagRepo.findByValue(tag.getValue()).orElse(tag))
				.collect(Collectors.toSet());

		newRecipe.setTags(tags);

		recipeRepo.save(newRecipe);
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
		Collection<Recipe> allRecipes = getRecipes().values();
		if (filter.isEmpty())
			return new ArrayList<>(allRecipes);
		else {
			Stream<Recipe> stream = allRecipes.stream();
			RecipeSearchParameters searchParameters = filter.get();
			Filters tagFilters = searchParameters.getTags();
			if (tagFilters.getIncludes().isPresent()) {
				stream = stream.filter(
						recipe -> recipe.getTags().stream().map(t -> t.toLowerCase()).collect(Collectors.toList()).containsAll(tagFilters.getIncludes().get()));
			}

			if (tagFilters.getExcludes().isPresent()) {
				stream = stream.filter(recipe -> Collections.disjoint(recipe.getTags().stream().map(t -> t.toLowerCase()).collect(Collectors.toList()),
						tagFilters.getExcludes().get()));
			}

			Filters ingredientFilters = searchParameters.getIngredients();
			if (ingredientFilters.getIncludes().isPresent()) {
				stream = stream.filter(recipe -> recipe.getIngredients().stream().map(i -> i.getName().toLowerCase()).collect(Collectors.toList())
						.containsAll(ingredientFilters.getIncludes().get()));
			}

			if (ingredientFilters.getExcludes().isPresent()) {
				stream = stream
						.filter(recipe -> Collections.disjoint(
								recipe.getIngredients().stream().map(i -> i.getName().toLowerCase()).collect(Collectors.toList()),
								ingredientFilters.getExcludes().get()));
			}

			return stream.collect(Collectors.toList());
		}
	}

	public void update(String id, Recipe recipe) throws IOException {
		getRecipes().put(id, recipe);
		save();
	}

}
