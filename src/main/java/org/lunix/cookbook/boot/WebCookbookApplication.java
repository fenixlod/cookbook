package org.lunix.cookbook.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("org.lunix.cookbook")
@SpringBootApplication
public class WebCookbookApplication {
	public static void main(String[] args) {
		SpringApplication.run(WebCookbookApplication.class, args);
	}
}
