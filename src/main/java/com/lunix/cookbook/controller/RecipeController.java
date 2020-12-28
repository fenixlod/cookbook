package com.lunix.cookbook.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lunix.cookbook.model.Recipe;
import com.lunix.cookbook.object.RecipeSearchFilter;
import com.lunix.cookbook.service.RecipeService;

@RestController
@RequestMapping(value = "/recipes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RecipeController {
	private RecipeService service;

	public RecipeController(RecipeService recipeService) {
		this.service = recipeService;
	}

	@PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addRecipe(@RequestBody Recipe newRecipe) {
		return service.addRecipe(newRecipe).get();
	}

	@GetMapping("")
	public ResponseEntity<?> listRecipes(@RequestParam(name = "tags", required = false) List<String> tagsFilter,
			@RequestParam(name = "limit", required = false) Integer limit,
			@RequestParam(name = "offset", required = false) Integer offset) {
		RecipeSearchFilter filter = new RecipeSearchFilter();
		filter.setLimit(limit);
		filter.setOffset(offset);
		filter.setTags(tagsFilter);
		return service.listRecipes(filter).get();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getRecipe(@PathVariable(name = "id", required = true) String recipeId) {
		return service.getRecipe(recipeId).get();
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateRecipe(@PathVariable(name = "id", required = true) String recipeId,
			@RequestBody Recipe updatedRecipe) {
		return service.updateRecipe(recipeId, updatedRecipe).get();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteRecipe(@PathVariable(name = "id", required = true) String recipeId) {
		return service.deleteRecipe(recipeId).get();
	}

	@GetMapping("/filters")
	public ResponseEntity<?> getRecipeFilters() {
		return service.getRecipeFilters().get();
	}
}
