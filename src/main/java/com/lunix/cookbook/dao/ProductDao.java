package com.lunix.cookbook.dao;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.lunix.cookbook.entity.Ingredient;
import com.lunix.cookbook.entity.Product;
import com.lunix.cookbook.repository.ProductRepository;

@Repository
public class ProductDao {
	private final ProductRepository productRepo;

	public ProductDao(ProductRepository productRepo) {
		this.productRepo = productRepo;
	}

	public Set<Product> getProductsByName(Collection<String> products) {
		return productRepo.findByNameIn(products);
	}

	public Set<Product> getProducts(Collection<Product> products) {
		return productRepo.findByNameIn(products.stream().map(p -> p.getName()).collect(Collectors.toSet()));
	}

	/**
	 * Search the DB and replace all ingredient's products with one found in the DB.
	 * If the product is not found then leave it as it is. This functionality
	 * ensures there are no duplicated products.
	 * 
	 * @param ingredients
	 * @return
	 */

	public Set<Ingredient> findIngredientProducts(Set<Ingredient> ingredients) {
		Set<Product> products = ingredients.stream().map(i -> i.getProduct()).collect(Collectors.toSet());
		Map<String, Product> foundProducts = getProducts(products).stream().collect(Collectors.toMap(p -> p.getName(), p -> p));
		ingredients.forEach(i -> i.setProduct(foundProducts.getOrDefault(i.getProduct().getName(), i.getProduct())));
		return ingredients;
	}
}
