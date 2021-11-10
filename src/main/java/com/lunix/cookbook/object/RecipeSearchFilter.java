package com.lunix.cookbook.object;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.util.MultiValueMap;

public class RecipeSearchFilter {
	private Filters tags;
	private Filters ingredients;
	private Optional<Integer> limit;
	private Optional<Integer> offset;

	public RecipeSearchFilter() {
	}

	public RecipeSearchFilter(MultiValueMap<String, String> queryParameters) {
		Optional<List<String>> incluseTags = getParametersList(queryParameters, "tags[includes][]");
		Optional<List<String>> excludeTags = getParametersList(queryParameters, "tags[excludes][]");
		Optional<List<String>> incluseIngredients = getParametersList(queryParameters, "ingredients[includes][]");
		Optional<List<String>> excluseIngredients = getParametersList(queryParameters, "ingredients[excludes][]");
		Optional<Integer> limit = getParametersInt(queryParameters, "limit");
		Optional<Integer> offset = getParametersInt(queryParameters, "offset");
		
		setTags(new Filters(incluseTags, excludeTags));
		setIngredients(new Filters(incluseIngredients, excluseIngredients));
		setLimit(limit);
		setOffset(offset);
	}

	public Filters getTags() {
		return tags;
	}

	public void setTags(Filters tags) {
		this.tags = tags;
	}

	public Filters getIngredients() {
		return ingredients;
	}

	public void setIngredients(Filters ingredients) {
		this.ingredients = ingredients;
	}

	public Optional<Integer> getLimit() {
		return limit;
	}

	public void setLimit(Optional<Integer> limit) {
		this.limit = limit;
	}

	public Optional<Integer> getOffset() {
		return offset;
	}

	public void setOffset(Optional<Integer> offset) {
		this.offset = offset;
	}

	public boolean isEmpty() {
		if ((tags == null || (tags.getIncludes().isEmpty() && tags.getExcludes().isEmpty()))
				&& (ingredients == null || (ingredients.getIncludes().isEmpty() && ingredients.getExcludes().isEmpty()))) {
			return true;
		}

		return false;
	}

	private Optional<List<String>> getParametersList(MultiValueMap<String, String> parameters, String listName) {
		List<String> paramList = parameters.getOrDefault(listName, Collections.emptyList());
		return Optional.of(paramList);
	}

	private Optional<Integer> getParametersInt(MultiValueMap<String, String> parameters, String paramName) {
		List<String> param = parameters.get(paramName);
		if (param == null)
			return Optional.empty();

		return Optional.of(Integer.valueOf(param.get(0)));
	}
}
