package com.lunix.cookbook.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.lunix.cookbook.dao.RecipeDao;
import com.lunix.cookbook.enums.CommonMessages;
import com.lunix.cookbook.model.Recipe;
import com.lunix.cookbook.object.Filters;
import com.lunix.cookbook.object.MealRequirement;
import com.lunix.cookbook.object.MenuDefinition;
import com.lunix.cookbook.object.MenuMeal;
import com.lunix.cookbook.object.MenuResult;
import com.lunix.cookbook.object.RecipeSearchParameters;
import com.lunix.cookbook.utility.OperationResult;

@Service
public class MenuService {
	private RecipeDao recipeDao;

	public MenuService(RecipeDao dao) {
		this.recipeDao = dao;
	}

	public OperationResult generateMenu(MenuDefinition menuDefinition) {
		try {
			List<MenuResult> results = new ArrayList<>();
			int countSuggestions = menuDefinition.getSettings().getSuggestions();
			for (MenuMeal meal : menuDefinition.getMeals()) {
				MenuResult result = new MenuResult();
				List<Recipe> suggestedRecipes = new ArrayList<>();

				result.setName(meal.getName());
				RecipeSearchParameters parameters = new RecipeSearchParameters();
				parameters.setIngredients(transformToFilter(meal.getIngredients()));
				parameters.setTags(transformToFilter(meal.getTags()));
				List<Recipe> possibleRecipes = recipeDao.getRecipes(Optional.of(parameters));
				
				for (int i = 0; i < countSuggestions; i++) {
					Optional<Recipe> randomRecipe = getRandomRecipe(possibleRecipes);
					if (randomRecipe.isEmpty())
						break;

					suggestedRecipes.add(randomRecipe.get());
				}
				result.setSuggestedRecipes(suggestedRecipes);
				results.add(result);
			}

			return OperationResult.ok(results);
		} catch (IOException e) {
			e.printStackTrace();
			return OperationResult.error(CommonMessages.GENERAL_ERROR.get());
		}
	}

	private Filters transformToFilter(MealRequirement requirement) {
		return new Filters(Optional.of(requirement.getInclude()), Optional.of(requirement.getExclude()));
	}

	private Optional<Recipe> getRandomRecipe(List<Recipe> recipes) {
		if (recipes.isEmpty())
			return Optional.empty();

		int random = 0;
		if (recipes.size() > 1) {
			random = ThreadLocalRandom.current().nextInt(0, recipes.size());
		}

		Recipe chosenRecipe = recipes.get(random);
		recipes.remove(random);
		return Optional.of(chosenRecipe);
	}
}
