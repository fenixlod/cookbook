package com.lunix.cookbook.utility;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.lunix.cookbook.dto.IngredientDto;

public class DuplicatedProductsValidator implements ConstraintValidator<UniqueProducts, List<IngredientDto>> {

	@Override
	public boolean isValid(List<IngredientDto> value, ConstraintValidatorContext context) {
		return value.stream().map(i -> i.getName()).distinct().count() == value.size();
	}
}
