package com.lunix.cookbook.service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lunix.cookbook.dao.RecipeDao;
import com.lunix.cookbook.enums.CommonMessages;
import com.lunix.cookbook.model.Recipe;
import com.lunix.cookbook.object.RecipeFilters;
import com.lunix.cookbook.object.RecipeSearchFilter;
import com.lunix.cookbook.utility.OperationResult;

@Service
public class RecipeService {
	private RecipeDao recipeDao;

	public RecipeService(RecipeDao dao) {
		this.recipeDao = dao;
	}

	public OperationResult addRecipe(Recipe newRecipe) {
		Optional<String> validationResult = validateRecipe(newRecipe);
		if (validationResult.isPresent())
			return OperationResult.invalid(validationResult.get());

		try {
			Optional<Recipe> recipe = recipeDao.getByName(newRecipe.getName());
			if (recipe.isPresent())
				return OperationResult.invalid("Рецепта с това име вече съществува");

			newRecipe.setId(UUID.nameUUIDFromBytes(newRecipe.getName().getBytes("UTF-8")).toString());
			recipeDao.insert(newRecipe);
			return OperationResult.created(recipeDao);
		} catch (IOException e) {
			e.printStackTrace();
			return OperationResult.error(CommonMessages.GENERAL_ERROR.get());
		}
	}

	public OperationResult listRecipes(RecipeSearchFilter searchFilter) {
		try {
			List<Recipe> foundRecipes = recipeDao.getRecipes(Optional.of(searchFilter));
			return OperationResult.ok(foundRecipes);
		} catch (IOException e) {
			e.printStackTrace();
			return OperationResult.error(CommonMessages.GENERAL_ERROR.get());
		}

	}

	public OperationResult getRecipe(String id) {
		try {
			Optional<Recipe> recipe = recipeDao.getById(id);
			if (recipe.isEmpty())
				return OperationResult.notFound("Рецептата не е открита");

			return OperationResult.ok(recipe.get());
		} catch (IOException e) {
			e.printStackTrace();
			return OperationResult.error(CommonMessages.GENERAL_ERROR.get());
		}
	}

	public OperationResult updateRecipe(String id, Recipe updatedRecipe) {
		Optional<String> validationResult = validateRecipe(updatedRecipe);
		if (validationResult.isPresent())
			return OperationResult.invalid(validationResult.get());

		try {
			Optional<Recipe> oldRecipe = recipeDao.getById(id);
			if (oldRecipe.isEmpty())
				return OperationResult.notFound("Рецептата не е открита");

			Optional<Recipe> recipe = recipeDao.getByName(updatedRecipe.getName());
			if (recipe.isPresent()) {
				if (!recipe.get().getId().equals(id))
					return OperationResult.invalid("Рецепта с това име вече съществува");
			}

			updatedRecipe.setId(id);
			recipeDao.update(id, updatedRecipe);
			return OperationResult.created(recipeDao);
		} catch (IOException e) {
			e.printStackTrace();
			return OperationResult.error(CommonMessages.GENERAL_ERROR.get());
		}
	}

	public OperationResult deleteRecipe(String id) {
		try {
			Optional<Recipe> recipe = recipeDao.getById(id);
			if (recipe.isEmpty())
				return OperationResult.notFound("Рецептата не е открита");

			recipeDao.delete(id);
			return OperationResult.accepted();
		} catch (IOException e) {
			e.printStackTrace();
			return OperationResult.error(CommonMessages.GENERAL_ERROR.get());
		}
	}

	private Optional<String> validateRecipe(Recipe recipe) {
		if (StringUtils.isEmpty(recipe.getName()))
			return Optional.of("Полето име е задължително");

		return Optional.empty();
	}

	public OperationResult getRecipeFilters() {
		try {
			RecipeFilters recipeFilters = new RecipeFilters();
			List<Recipe> allRecipes = recipeDao.getRecipes(Optional.empty());

			Map<String, Long> tags = allRecipes.stream().map(r -> r.getTags()).flatMap(Collection::stream).map(t -> t.toLowerCase())
					.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

			Map<String, Long> ingredients = allRecipes.stream().map(r -> r.getIngredients()).flatMap(Collection::stream).map(i -> i.getName().toLowerCase())
					.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

			recipeFilters.setTags(tags);
			recipeFilters.setIngredients(ingredients);
			return OperationResult.ok(recipeFilters);
		} catch (IOException e) {
			e.printStackTrace();
			return OperationResult.error(CommonMessages.GENERAL_ERROR.get());
		}

	}
}
