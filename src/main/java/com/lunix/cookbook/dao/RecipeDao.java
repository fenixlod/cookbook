package com.lunix.cookbook.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.lunix.cookbook.entity.Product;
import com.lunix.cookbook.entity.Recipe;
import com.lunix.cookbook.entity.Tag;
import com.lunix.cookbook.model.RecipeOld;
import com.lunix.cookbook.object.Filters;
import com.lunix.cookbook.object.RecipeSearchParameters;
import com.lunix.cookbook.repository.LocalJsonDatabase;
import com.lunix.cookbook.repository.ProductRepository;
import com.lunix.cookbook.repository.RecipeRepository;
import com.lunix.cookbook.repository.TagRepository;

@Repository
public class RecipeDao {
	private LocalJsonDatabase<RecipeOld> db;
	private Map<String, RecipeOld> recipes;
	private final RecipeRepository recipeRepo;
	private final TagRepository tagRepo;
	private final ProductRepository productRepo;

	public RecipeDao(LocalJsonDatabase<RecipeOld> database, RecipeRepository recipeRepo, TagRepository tagRepo, ProductRepository productRepo) {
		this.db = database;
		this.recipeRepo = recipeRepo;
		this.tagRepo = tagRepo;
		this.productRepo = productRepo;
	}

	public void save() throws IOException {
		db.save(recipes.values());
	}

	private synchronized Map<String, RecipeOld> getRecipes() throws IOException {
		if (recipes == null) {
			recipes = new HashMap<>();
			db.load().forEach(obj -> {
				recipes.put(obj.getId(), obj);
			});
		}

		return recipes;
	}

	public void createNew(Recipe newRecipe) {
		Set<Tag> mappedTags = newRecipe.getTags()
				.stream()
				.map(t -> tagRepo.findByValue(t.getValue()).orElse(t))
				.collect(Collectors.toSet());
		newRecipe.setTags(mappedTags);
		List<Product> products = newRecipe.getIngredients().parallelStream().map(i -> i.getProduct()).collect(Collectors.toList());
		List<Product> savedProducts = productRepo.saveAll(products);
		newRecipe.getIngredients().forEach(i -> i.setProduct(savedProducts.get(0)));
		recipeRepo.save(newRecipe);
	}

	public Optional<RecipeOld> getByName(String recipeName) throws IOException {
		List<RecipeOld> foundRecipes = getRecipes().values().stream().filter(r -> r.getName().equalsIgnoreCase(recipeName)).collect(Collectors.toList());
		if (foundRecipes.isEmpty())
			return Optional.empty();

		return Optional.of(foundRecipes.get(0));
	}

	public Optional<RecipeOld> getById(String recipeId) throws IOException {
		RecipeOld foundRecipe = getRecipes().get(recipeId);
		if (foundRecipe == null)
			return Optional.empty();

		return Optional.of(foundRecipe);
	}

	public void delete(String recipeId) throws IOException {
		getRecipes().remove(recipeId);
		save();
	}

	public List<RecipeOld> getRecipes(Optional<RecipeSearchParameters> filter) throws IOException {
		Collection<RecipeOld> allRecipes = getRecipes().values();
		if (filter.isEmpty())
			return new ArrayList<>(allRecipes);
		else {
			Stream<RecipeOld> stream = allRecipes.stream();
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

	public void update(String id, RecipeOld recipe) throws IOException {
		getRecipes().put(id, recipe);
		save();
	}

}
