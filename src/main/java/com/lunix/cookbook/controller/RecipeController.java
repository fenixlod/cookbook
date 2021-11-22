package com.lunix.cookbook.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lunix.cookbook.dto.RecipeDto;
import com.lunix.cookbook.object.RecipeSearchFilter;
import com.lunix.cookbook.service.RecipeService;

@RestController
@RequestMapping(value = "/recipes", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class RecipeController {
	private RecipeService service;

	public RecipeController(RecipeService recipeService) {
		this.service = recipeService;
	}

	@PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createRecipe(@RequestBody @Valid RecipeDto newRecipe) {
		return service.createRecipe(newRecipe).get();
	}

	@GetMapping("")
	public ResponseEntity<?> listRecipes(@RequestParam MultiValueMap<String, String> queryParameters) {
		return service.listRecipes(new RecipeSearchFilter(queryParameters)).get();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getRecipe(@PathVariable(name = "id", required = true) @NotBlank(message = "Невалидно ID") String identifier) {
		return service.getRecipe(identifier).get();
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateRecipe(@PathVariable(name = "id", required = true) @NotBlank(message = "Невалидно ID") String identifier,
			@RequestBody @Valid RecipeDto updatedRecipe) {
		return service.updateRecipe(identifier, updatedRecipe).get();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteRecipe(@PathVariable(name = "id", required = true) @NotBlank(message = "Невалидно ID") String identifier) {
		return service.deleteRecipe(identifier).get();
	}

	@GetMapping("/filters")
	public ResponseEntity<?> getRecipeFilters() {
		return service.getRecipeFilters().get();
	}
}
