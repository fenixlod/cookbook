package com.lunix.cookbook.object;

import java.util.List;

public class MenuDefinition {
	private Object settings;
	private List<MenuMeal> meals;

	public Object getSettings() {
		return settings;
	}

	public void setSettings(Object settings) {
		this.settings = settings;
	}

	public List<MenuMeal> getMeals() {
		return meals;
	}

	public void setMeals(List<MenuMeal> meals) {
		this.meals = meals;
	}
}
