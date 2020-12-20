package org.lunix.cookbook.controller;

import java.util.ArrayList;
import java.util.List;

import org.lunix.cookbook.model.Recipe;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/recipes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RecipeController {

	@GetMapping("")
	public ResponseEntity<?> listRecipes(@RequestParam(name = "tags", required = false) List<String> tagsFilter) {
		List<Recipe> recipes = new ArrayList<>();
		Recipe recipe = new Recipe();
		recipe.setName("Banica");
		recipes.add(recipe);
		return new ResponseEntity<Object>(recipes, HttpStatus.OK);
	}
}
