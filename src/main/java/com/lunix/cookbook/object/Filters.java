package com.lunix.cookbook.object;

import java.util.List;

public class Filters {
	private List<String> includes;
	private List<String> excludes;

	public List<String> getIncludes() {
		return includes;
	}

	public void setIncludes(List<String> includes) {
		this.includes = includes;
	}

	public List<String> getExcludes() {
		return excludes;
	}

	public void setExcludes(List<String> excludes) {
		this.excludes = excludes;
	}
}
