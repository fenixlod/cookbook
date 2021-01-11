package com.lunix.cookbook.object;

import java.util.List;

public class MenuDefinition {
	private MenuSettings settings;
	private List<MenuMeal> meals;

	public MenuSettings getSettings() {
		return settings;
	}

	public void setSettings(MenuSettings settings) {
		this.settings = settings;
	}

	public List<MenuMeal> getMeals() {
		return meals;
	}

	public void setMeals(List<MenuMeal> meals) {
		this.meals = meals;
	}
}
