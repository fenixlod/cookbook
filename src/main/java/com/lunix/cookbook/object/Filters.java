package com.lunix.cookbook.object;

import java.util.List;
import java.util.Optional;

public class Filters {
	private Optional<List<String>> includes;
	private Optional<List<String>> excludes;

	public Filters(Optional<List<String>> includes, Optional<List<String>> excludes) {
		this.includes = includes;
		this.excludes = excludes;
	}

	public Optional<List<String>> getIncludes() {
		return includes;
	}

	public void setIncludes(Optional<List<String>> includes) {
		this.includes = includes;
	}

	public Optional<List<String>> getExcludes() {
		return excludes;
	}

	public void setExcludes(Optional<List<String>> excludes) {
		this.excludes = excludes;
	}
}
