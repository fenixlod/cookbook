package com.lunix.cookbook.object;

import java.util.List;

public class MealRequirement {
	private List<String> include;
	private List<String> exclude;

	public List<String> getInclude() {
		return include;
	}

	public void setInclude(List<String> include) {
		this.include = include;
	}

	public List<String> getExclude() {
		return exclude;
	}

	public void setExclude(List<String> exclude) {
		this.exclude = exclude;
	}
}
