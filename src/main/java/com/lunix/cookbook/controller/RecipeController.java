package com.lunix.cookbook.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.http.HttpStatus;
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
import com.lunix.cookbook.entity.Recipe;
import com.lunix.cookbook.object.RecipeSearchFilter;
import com.lunix.cookbook.service.RecipeService;
import com.lunix.cookbook.utility.RecipeConverter;

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
		Recipe createdRecipe = service.createRecipe(RecipeConverter.toEntity(newRecipe));
		return new ResponseEntity<>(RecipeConverter.toDto(createdRecipe), HttpStatus.OK);
	}

	@GetMapping("")
	public ResponseEntity<?> listRecipes(@RequestParam MultiValueMap<String, String> queryParameters) {
		List<Recipe> recipes = service.listRecipes(new RecipeSearchFilter(queryParameters));
		return new ResponseEntity<>(recipes.stream().map(RecipeConverter::toDto).collect(Collectors.toList()), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getRecipe(@PathVariable(name = "id", required = true) @NotBlank(message = "Невалидно ID") String identifier) {
		Recipe foundRecipe = service.getRecipe(identifier);
		return new ResponseEntity<>(RecipeConverter.toDto(foundRecipe), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateRecipe(@PathVariable(name = "id", required = true) @NotBlank(message = "Невалидно ID") String identifier,
			@RequestBody @Valid RecipeDto recipeToUpdate) {
		Recipe updatedRecipe = service.updateRecipe(identifier, RecipeConverter.toEntity(recipeToUpdate));
		return new ResponseEntity<>(RecipeConverter.toDto(updatedRecipe), HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteRecipe(@PathVariable(name = "id", required = true) @NotBlank(message = "Невалидно ID") String identifier) {
		service.deleteRecipe(identifier);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/filters")
	public ResponseEntity<?> getRecipeFilters() {
		return new ResponseEntity<>(service.getRecipeFilters(), HttpStatus.OK);
	}
}
