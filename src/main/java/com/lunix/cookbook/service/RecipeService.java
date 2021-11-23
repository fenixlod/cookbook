package com.lunix.cookbook.service;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lunix.cookbook.dao.RecipeDao;
import com.lunix.cookbook.entity.Recipe;
import com.lunix.cookbook.object.RecipeFilterCounts;
import com.lunix.cookbook.object.RecipeSearchFilter;

@Service
public class RecipeService {
	private RecipeDao recipeDao;

	public RecipeService(RecipeDao dao) {
		this.recipeDao = dao;
	}

	public Recipe createRecipe(Recipe newRecipe) throws ResponseStatusException {
		if (recipeDao.getByName(newRecipe.getName()).isPresent())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Рецепта с това име вече съществува");

		return recipeDao.createNew(newRecipe);
	}

	public List<Recipe> listRecipes(RecipeSearchFilter searchParameters) {
		return recipeDao.findBy(searchParameters);
	}

	public Recipe getRecipe(String identifier) throws ResponseStatusException {
		Optional<Recipe> recipe = recipeDao.getByIdentifier(identifier);
		if (recipe.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Рецептата не е открита");

		return recipe.get();
	}

	public Recipe updateRecipe(String identifier, Recipe updatedRecipe) throws ResponseStatusException {
		Optional<Recipe> oldRecipe = recipeDao.getByIdentifier(identifier);
		if (oldRecipe.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Рецептата не е открита");

		Optional<Recipe> recipe = recipeDao.getByName(updatedRecipe.getName());
		if (recipe.isPresent()) {
			if (!recipe.get().getIdentifier().equals(identifier))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Рецепта с това име вече съществува");
		}

		return recipeDao.update(oldRecipe.get(), updatedRecipe);
	}

	public void deleteRecipe(String identifier) throws ResponseStatusException {
		Optional<Recipe> recipe = recipeDao.getByIdentifier(identifier);
		if (recipe.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Рецептата не е открита");

		recipeDao.delete(recipe.get());
	}

	public RecipeFilterCounts getRecipeFilters() {
		RecipeFilterCounts recipeFilters = new RecipeFilterCounts();
		List<Recipe> allRecipes = recipeDao.getAll();

		Map<String, Long> tags = allRecipes.stream().map(r -> r.getTags()).flatMap(Collection::stream).map(t -> t.getValue().toLowerCase())
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

		Map<String, Long> sortedTags = tags.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

		Map<String, Long> ingredients = allRecipes.stream().map(r -> r.getIngredients()).flatMap(Collection::stream)
				.map(i -> i.getProduct().getName().toLowerCase())
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

		Map<String, Long> sortedIngredients = ingredients.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

		recipeFilters.setTags(sortedTags);
		recipeFilters.setIngredients(sortedIngredients);
		return recipeFilters;
	}
}
