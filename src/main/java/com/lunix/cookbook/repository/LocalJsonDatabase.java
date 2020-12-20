package com.lunix.cookbook.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.lunix.cookbook.repository.DatabaseProperties.DatabaseConfig;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LocalJsonDatabase<T> {
	private DatabaseConfig configuration;
	private ObjectMapper mapper;
	private Class<T> classType;

	public LocalJsonDatabase(DatabaseConfig configuration, ObjectMapper mapper, Class<T> classType) {
		this.configuration = configuration;
		this.mapper = mapper;
		this.classType = classType;
	}

	public void save(Collection<T> elements) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(configuration.getStoreFile())), "UTF-8"))) {
			for (T element : elements) {
				writer.write(mapper.writeValueAsString(element));
				writer.newLine();
			}
		}
	}

	public List<T> load() throws IOException {
		List<T> collection = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(configuration.getStoreFile())), "UTF-8"))) {
			reader.lines().forEach(l -> {
				try {
					T obj = mapper.readValue(l, classType);
					collection.add(obj);
				} catch (IOException e) {
					System.out.println("Unable to parse object in store file: " + configuration.getStoreFile());
					e.printStackTrace();
				}
			});
			return collection;
		} catch (FileNotFoundException e) {
			System.out.println("Store file: " + configuration.getStoreFile() + " not found!");
			return Collections.emptyList();
		}
	}
}
