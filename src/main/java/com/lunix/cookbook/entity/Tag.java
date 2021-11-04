package com.lunix.cookbook.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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

	@ManyToMany(targetEntity = Recipe.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "tag_recipes", joinColumns = @JoinColumn(name = "tag_id"), inverseJoinColumns = @JoinColumn(name = "recipe_id"))
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
}
