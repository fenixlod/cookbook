package com.lunix.cookbook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lunix.cookbook.entity.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
	public List<Recipe> findAllByNameIgnoreCase(String recipeName);

}
