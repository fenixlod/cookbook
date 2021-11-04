package com.lunix.cookbook.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tag")
public class Tag {
	@Id
	@GeneratedValue(generator = "tag_generator")
	private Long id;

	@Column(unique = true)
	private String value;

	@ManyToMany(targetEntity = Recipe.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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
