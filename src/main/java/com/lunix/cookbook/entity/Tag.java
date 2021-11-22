package com.lunix.cookbook.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "tag", uniqueConstraints = @UniqueConstraint(name = "UniqueValue", columnNames = "value"))
public class Tag {
	@Id
	@GeneratedValue(generator = "tag_generator")
	private Long id;

	@Column(unique = true)
	private String value;

	@ManyToMany(mappedBy = "tags")
	private List<Recipe> recipes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<Recipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<Recipe> recipes) {
		this.recipes = recipes;
	}
}
