package com.lunix.cookbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lunix.cookbook.entity.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

}
