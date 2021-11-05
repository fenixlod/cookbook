package com.lunix.cookbook.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lunix.cookbook.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	public Optional<Product> findByName(String name);
}
