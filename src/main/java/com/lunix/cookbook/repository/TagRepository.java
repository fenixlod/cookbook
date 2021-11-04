package com.lunix.cookbook.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lunix.cookbook.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer> {
	public Optional<Tag> findByValue(String value);
}
