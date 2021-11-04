package com.lunix.cookbook.repository;

import com.lunix.cookbook.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
  Optional<Tag> findByValue(String value);
}
