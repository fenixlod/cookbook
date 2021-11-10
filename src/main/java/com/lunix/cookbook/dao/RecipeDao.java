package com.lunix.cookbook.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.lunix.cookbook.entity.Ingredient;
import com.lunix.cookbook.entity.Recipe;
import com.lunix.cookbook.object.Filters;
import com.lunix.cookbook.object.RecipeSearchFilter;
import com.lunix.cookbook.repository.IngredientRepository;
import com.lunix.cookbook.repository.RecipeRepository;
import com.lunix.cookbook.repository.TagRepository;

@Repository
public class RecipeDao {
	private final RecipeRepository recipeRepo;
	private final ProductDao productDao;
	private final TagDao tagDao;
	private final IngredientRepository ingredientRepo;

	public RecipeDao(RecipeRepository recipeRepo, TagRepository tagRepo, ProductDao productDao, TagDao tagDao,
			IngredientRepository ingredientRepo) {
		this.recipeRepo = recipeRepo;
		this.productDao = productDao;
		this.tagDao = tagDao;
		this.ingredientRepo = ingredientRepo;
	}

	public Recipe createNew(Recipe newRecipe) {
		newRecipe.setTags(tagDao.findTags(newRecipe.getTags()));
		newRecipe.setIngredients(productDao.findIngredientProducts(newRecipe.getIngredients()));
		newRecipe.setIdentifier(UUID.randomUUID().toString());
		return recipeRepo.save(newRecipe);
	}

	public Optional<Recipe> getByName(String recipeName) {
		List<Recipe> foundRecipes = recipeRepo.findAllByNameIgnoreCase(recipeName);
		if (foundRecipes.isEmpty())
			return Optional.empty();

		return Optional.of(foundRecipes.get(0));
	}

	public Optional<Recipe> getByIdentifier(String recipeId) {
		return recipeRepo.findByIdentifier(recipeId);
	}

	public void delete(Recipe recipeToDelete) {
		// TODO Fix me! Deleteing is cascaded!!
		recipeRepo.delete(recipeToDelete);
	}

	public List<Recipe> findBy(RecipeSearchFilter filters) {
		List<Recipe> allRecipes = getAll();

		if (filters.isEmpty())
			return allRecipes;
		else {
			Stream<Recipe> stream = allRecipes.stream();
			Filters tagFilters = filters.getTags();
			if (tagFilters.getIncludes().isPresent()) {
				stream = stream.filter(
						recipe -> recipe.getTags().stream().map(t -> t.getValue().toLowerCase()).collect(Collectors.toList())
								.containsAll(tagFilters.getIncludes().get()));
			}

			if (tagFilters.getExcludes().isPresent()) {
				stream = stream.filter(recipe -> Collections
						.disjoint(recipe.getTags().stream().map(t -> t.getValue().toLowerCase()).collect(Collectors.toList()),
						tagFilters.getExcludes().get()));
			}

			Filters ingredientFilters = filters.getIngredients();
			if (ingredientFilters.getIncludes().isPresent()) {
				stream = stream.filter(recipe -> recipe.getIngredients().stream().map(i -> i.getProduct().getName().toLowerCase()).collect(Collectors.toList())
						.containsAll(ingredientFilters.getIncludes().get()));
			}

			if (ingredientFilters.getExcludes().isPresent()) {
				stream = stream
						.filter(recipe -> Collections.disjoint(
								recipe.getIngredients().stream().map(i -> i.getProduct().getName().toLowerCase()).collect(Collectors.toList()),
								ingredientFilters.getExcludes().get()));
			}

			return stream.collect(Collectors.toList());
		}
	}

	public List<Recipe> getAll() {
		return recipeRepo.findAll();
	}

	public Recipe update(Recipe oldRecipe, Recipe newRecipe) {
		oldRecipe.setDescription(newRecipe.getDescription());
		oldRecipe.setName(newRecipe.getName());
		oldRecipe.setPreparation(newRecipe.getPreparation());
		oldRecipe.setTags(tagDao.findTags(newRecipe.getTags()));
		
		Map<String, Ingredient> newIngredientsMap = newRecipe.getIngredients().stream().collect(Collectors.toMap(i -> i.getProduct().getName(), i -> i));
		List<Ingredient> ingredientsToRemove = new ArrayList<>();
		Set<Ingredient> updatedIngredients = new HashSet<>();
		for(Ingredient i : oldRecipe.getIngredients()) {
			Ingredient newIngredient = newIngredientsMap.get(i.getProduct().getName());

			if (newIngredient == null) {
				ingredientsToRemove.add(i);
			} else {
				i.setAmount(newIngredient.getAmount());
				i.setUnit(newIngredient.getUnit());
				updatedIngredients.add(i);
				newIngredientsMap.remove(i.getProduct().getName());
			}
		}
		
		Set<Ingredient> ingredientsToAdd = new HashSet<>(newIngredientsMap.values());
		ingredientsToAdd = productDao.findIngredientProducts(ingredientsToAdd);
		ingredientsToAdd.forEach(i -> i.setRecipe(oldRecipe));
		updatedIngredients.addAll(ingredientsToAdd);
		oldRecipe.setIngredients(updatedIngredients);
		Recipe updatedRecipe = recipeRepo.save(oldRecipe);

		if (!ingredientsToRemove.isEmpty())
			ingredientRepo.deleteInBatch(ingredientsToRemove);

		return updatedRecipe;
	}
}
