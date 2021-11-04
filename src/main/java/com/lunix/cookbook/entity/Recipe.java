package com.lunix.cookbook.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

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
	 @JoinTable(name = "recipe_tag", joinColumns = @JoinColumn(name = "recipe_id"),
	 inverseJoinColumns = @JoinColumn(name = "tag_id"))
	private Set<Tag> tags = new HashSet<>();

	@OneToMany(targetEntity = Ingredient.class, cascade = CascadeType.ALL)
	// @JoinTable(name = "recipe_ingredient", joinColumns = @JoinColumn(name =
	// "id"), inverseJoinColumns = @JoinColumn(name = "id"))
	private List<Ingredient> ingredients;

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
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
