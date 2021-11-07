package com.lunix.cookbook.repository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lunix.cookbook.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	public Optional<Product> findByName(String name);

	public Set<Product> findByNameIn(Collection<String> names);
}
