package com.lunix.cookbook.service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lunix.cookbook.dao.RecipeDao;
import com.lunix.cookbook.enums.CommonMessages;
import com.lunix.cookbook.model.Recipe;
import com.lunix.cookbook.object.MenuDefinition;
import com.lunix.cookbook.utility.OperationResult;

@Service
public class MenuService {
	private RecipeDao recipeDao;

	public MenuService(RecipeDao dao) {
		this.recipeDao = dao;
	}

	public OperationResult generateMenu(MenuDefinition menuDefinition) {
		try {
			List<Recipe> recipes = recipeDao.getRecipes(Optional.empty());
			return OperationResult.ok(Collections.emptyList());
		} catch (IOException e) {
			e.printStackTrace();
			return OperationResult.error(CommonMessages.GENERAL_ERROR.get());
		}
	}
}
