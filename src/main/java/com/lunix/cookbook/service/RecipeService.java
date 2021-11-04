package com.lunix.cookbook.service;

import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lunix.cookbook.dao.RecipeDao;
import com.lunix.cookbook.enums.CommonMessages;
import com.lunix.cookbook.exception.RecipeValidationException;
import com.lunix.cookbook.model.Recipe;
import com.lunix.cookbook.object.RecipeFilterCounts;
import com.lunix.cookbook.object.RecipeSearchParameters;
import com.lunix.cookbook.utility.OperationResult;

@Service
public class RecipeService {
	private RecipeDao recipeDao;

	public RecipeService(RecipeDao dao) {
		this.recipeDao = dao;
	}

	public OperationResult createRecipe(com.lunix.cookbook.entity.Recipe newRecipe) throws RecipeValidationException, IOException {
		validateRecipe(newRecipe);
		if (recipeDao.getByName(newRecipe.getName()).isPresent())
			throw new RecipeValidationException("Рецепта с това име вече съществува");

		recipeDao.createNew(newRecipe);
		return OperationResult.created(recipeDao);
	}

	public OperationResult listRecipes(RecipeSearchParameters searchParameters) {
		try {
			List<Recipe> foundRecipes = recipeDao.getRecipes(Optional.of(searchParameters));
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

	public OperationResult updateRecipe(String id, Recipe updatedRecipe) throws RecipeValidationException {
		// validateRecipe(updatedRecipe);

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

	private void validateRecipe(com.lunix.cookbook.entity.Recipe recipe) throws RecipeValidationException {
		if (StringUtils.isEmpty(recipe.getName()))
			throw new RecipeValidationException("Име на рецептата е задължително поле");
	}

	public OperationResult getRecipeFilters() {
		try {
			RecipeFilterCounts recipeFilters = new RecipeFilterCounts();
			List<Recipe> allRecipes = recipeDao.getRecipes(Optional.empty());

			Map<String, Long> tags = allRecipes.stream().map(r -> r.getTags()).flatMap(Collection::stream).map(t -> t.toLowerCase())
					.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

			Map<String, Long> sortedTags = tags.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

			Map<String, Long> ingredients = allRecipes.stream().map(r -> r.getIngredients()).flatMap(Collection::stream).map(i -> i.getName().toLowerCase())
					.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

			Map<String, Long> sortedIngredients = ingredients.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

			recipeFilters.setTags(sortedTags);
			recipeFilters.setIngredients(sortedIngredients);
			return OperationResult.ok(recipeFilters);
		} catch (IOException e) {
			e.printStackTrace();
			return OperationResult.error(CommonMessages.GENERAL_ERROR.get());
		}

	}
}
