package com.lunix.cookbook.service;

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
import com.lunix.cookbook.exception.RecipeValidationException;
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

	public OperationResult createRecipe(RecipeDto newRecipe) throws RecipeValidationException {
		validateRecipe(newRecipe);

		if (recipeDao.getByName(newRecipe.getName()).isPresent())
			throw new RecipeValidationException("Рецепта с това име вече съществува");

		Recipe createdRecipe = recipeDao.createNew(RecipeConverter.toEntity(newRecipe));
		return OperationResult.created(RecipeConverter.toDto(createdRecipe));
	}

	public OperationResult listRecipes(RecipeSearchFilter searchParameters) {
		List<Recipe> foundRecipes = recipeDao.findBy(searchParameters);
		return OperationResult.ok(foundRecipes.stream().map(RecipeConverter::toDto).collect(Collectors.toList()));
	}

	public OperationResult getRecipe(String identifier) throws RecipeValidationException {
		if (StringUtils.isEmpty(identifier))
			throw new RecipeValidationException("ID на рецепта не може да е празно");

		Optional<Recipe> recipe = recipeDao.getByIdentifier(identifier);
		if (recipe.isEmpty())
			return OperationResult.notFound("Рецептата не е открита");

		return OperationResult.ok(RecipeConverter.toDto(recipe.get()));
	}

	public OperationResult updateRecipe(String identifier, RecipeDto updatedRecipe) {
		validateRecipe(updatedRecipe);

		Optional<Recipe> oldRecipe = recipeDao.getByIdentifier(identifier);
		if (oldRecipe.isEmpty())
			return OperationResult.notFound("Рецептата не е открита");

		Optional<Recipe> recipe = recipeDao.getByName(updatedRecipe.getName());
		if (recipe.isPresent()) {
			if (!recipe.get().getIdentifier().equals(identifier))
				throw new RecipeValidationException("Рецепта с това име вече съществува");
		}

		Recipe entity = recipeDao.update(oldRecipe.get(), RecipeConverter.toEntity(updatedRecipe));
		return OperationResult.created(RecipeConverter.toDto(entity));
	}

	public OperationResult deleteRecipe(String identifier) {
		Optional<Recipe> recipe = recipeDao.getByIdentifier(identifier);
		if (recipe.isEmpty())
			return OperationResult.notFound("Рецептата не е открита");

		recipeDao.delete(recipe.get());
		return OperationResult.accepted();
	}

	private void validateRecipe(RecipeDto recipe) throws RecipeValidationException {
		if (StringUtils.isEmpty(recipe.getName()))
			throw new RecipeValidationException("Име на рецептата е задължително поле");

		if (recipe.getTags().stream().distinct().count() != recipe.getTags().size())
			throw new RecipeValidationException("Рецептата съдържа дублиращи се тагове");

		if (recipe.getIngredients().stream().map(i -> i.getName()).distinct().count() != recipe.getIngredients().size())
			throw new RecipeValidationException("Рецептата съдържа дублиращи се продукта");
	}

	public OperationResult getRecipeFilters() {
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
		return OperationResult.ok(recipeFilters);

	}
}
