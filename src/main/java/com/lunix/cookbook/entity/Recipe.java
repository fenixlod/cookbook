package com.lunix.cookbook.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "recipe")
public class Recipe {
	@Id
	@GeneratedValue(generator = "recipe_generator")
	private Long id;
	private String name;
	private String description;
	private String preparation;

	@ManyToMany(targetEntity = Tag.class, cascade = CascadeType.ALL)
	// @JoinTable(name = "recipe_tag", joinColumns = @JoinColumn(name = "id"),
	// inverseJoinColumns = @JoinColumn(name = "id"))
	private List<Tag> tags;

	@OneToMany(targetEntity = Ingredient.class, cascade = CascadeType.ALL)
	// @JoinTable(name = "recipe_ingredient", joinColumns = @JoinColumn(name =
	// "id"), inverseJoinColumns = @JoinColumn(name = "id"))
	private List<Ingredient> ingredients;

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPreparation() {
		return preparation;
	}

	public void setPreparation(String preparation) {
		this.preparation = preparation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
