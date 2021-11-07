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
import com.lunix.cookbook.dto.RecipeDto;
import com.lunix.cookbook.entity.Recipe;
import com.lunix.cookbook.enums.CommonMessages;
import com.lunix.cookbook.exception.RecipeValidationException;
import com.lunix.cookbook.model.RecipeOld;
import com.lunix.cookbook.object.OperationResult;
import com.lunix.cookbook.object.RecipeFilterCounts;
import com.lunix.cookbook.object.RecipeSearchFilter;
import com.lunix.cookbook.utility.RecipeConverter;

@Service
public class RecipeService {
	private RecipeDao recipeDao;

	public RecipeService(RecipeDao dao) {
		this.recipeDao = dao;
	}

	public OperationResult createRecipe(RecipeDto newRecipe) throws RecipeValidationException, IOException {
		validateRecipe(newRecipe);
		if (recipeDao.getByName(newRecipe.getName()).isPresent())
			throw new RecipeValidationException("Рецепта с това име вече съществува");

		recipeDao.createNew(RecipeConverter.toEntity(newRecipe));
		return OperationResult.created(newRecipe);
	}

	public OperationResult listRecipes(RecipeSearchFilter searchParameters) {
		try {
			List<RecipeOld> foundRecipes = recipeDao.getRecipes(Optional.of(searchParameters));
			return OperationResult.ok(foundRecipes);
		} catch (IOException e) {
			e.printStackTrace();
			return OperationResult.error(CommonMessages.GENERAL_ERROR.get());
		}

	}

	public OperationResult getRecipe(String id) {
		try {
			Optional<RecipeOld> recipe = recipeDao.getById(id);
			if (recipe.isEmpty())
				return OperationResult.notFound("Рецептата не е открита");

			return OperationResult.ok(recipe.get());
		} catch (IOException e) {
			e.printStackTrace();
			return OperationResult.error(CommonMessages.GENERAL_ERROR.get());
		}
	}

	public OperationResult updateRecipe(String id, RecipeOld updatedRecipe) throws RecipeValidationException {
		// validateRecipe(updatedRecipe);

		try {
			Optional<RecipeOld> oldRecipe = recipeDao.getById(id);
			if (oldRecipe.isEmpty())
				return OperationResult.notFound("Рецептата не е открита");

			Optional<Recipe> recipe = recipeDao.getByName(updatedRecipe.getName());
			if (recipe.isPresent()) {
				if (!recipe.get().getId().equals(Long.valueOf(id)))
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
			Optional<RecipeOld> recipe = recipeDao.getById(id);
			if (recipe.isEmpty())
				return OperationResult.notFound("Рецептата не е открита");

			recipeDao.delete(id);
			return OperationResult.accepted();
		} catch (IOException e) {
			e.printStackTrace();
			return OperationResult.error(CommonMessages.GENERAL_ERROR.get());
		}
	}

	private void validateRecipe(RecipeDto recipe) throws RecipeValidationException {
		if (StringUtils.isEmpty(recipe.getName()))
			throw new RecipeValidationException("Име на рецептата е задължително поле");
	}

	public OperationResult getRecipeFilters() {
		try {
			RecipeFilterCounts recipeFilters = new RecipeFilterCounts();
			List<RecipeOld> allRecipes = recipeDao.getRecipes(Optional.empty());

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
