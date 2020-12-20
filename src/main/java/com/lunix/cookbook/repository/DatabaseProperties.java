package com.lunix.cookbook.repository;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "database")
public class DatabaseProperties {
	public static class DatabaseConfig {
		private String storeFile;

		public String getStoreFile() {
			return storeFile;
		}

		public void setStoreFile(String storeFile) {
			this.storeFile = storeFile;
		}
	}

	private DatabaseConfig recipe;

	public DatabaseConfig getRecipe() {
		return recipe;
	}

	public void setRecipe(DatabaseConfig recipe) {
		this.recipe = recipe;
	}
}
