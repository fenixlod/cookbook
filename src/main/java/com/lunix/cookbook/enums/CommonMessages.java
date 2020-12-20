package com.lunix.cookbook.enums;

public enum CommonMessages {
	GENERAL_ERROR("Възникна грешка при изпълнението на тази операция");

	private String value;

	private CommonMessages(String value) {
		this.value = value;
	}

	public String get() {
		return value;
	}
}
