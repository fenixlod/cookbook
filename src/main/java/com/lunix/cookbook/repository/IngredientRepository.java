package com.lunix.cookbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lunix.cookbook.entity.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {

}
