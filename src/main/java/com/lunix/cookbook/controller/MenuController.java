package com.lunix.cookbook.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lunix.cookbook.object.MenuDefinition;
import com.lunix.cookbook.object.MenuResult;
import com.lunix.cookbook.service.MenuService;

@RestController
@RequestMapping(value = "/menu", produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuController {
	private MenuService service;

	public MenuController(MenuService recipeService) {
		this.service = recipeService;
	}

	@PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> generateMenu(@RequestBody MenuDefinition menuDefinition) {
		List<MenuResult> menu = service.generateMenu(menuDefinition);
		return new ResponseEntity<>(menu, HttpStatus.OK);
	}
}
