package org.lunix.cookbook.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.lunix.cookbook.boot.WebCookbookApplication;
import org.lunix.cookbook.controller.RecipeController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(classes = WebCookbookApplication.class)
@AutoConfigureMockMvc
public class RecipeControllerTest {
	@Autowired
	private RecipeController controller;

	@Autowired
	private MockMvc mvc;

	@Test
	public void smokeTest() throws Exception {
		System.out.println("Start smoke test");
		assertThat(controller).isNotNull();
	}

	@Test
	public void testListRecipes() throws Exception {
		System.out.println("Start list recipes test");
		mvc.perform(MockMvcRequestBuilders.get("/recipes").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value("Banica"));
	}
}
