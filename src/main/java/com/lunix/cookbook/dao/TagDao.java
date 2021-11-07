package com.lunix.cookbook.dao;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.lunix.cookbook.entity.Tag;
import com.lunix.cookbook.repository.TagRepository;

@Repository
public class TagDao {
	private final TagRepository tagRepo;

	public TagDao(TagRepository tagRepo) {
		this.tagRepo = tagRepo;
	}

	public Set<Tag> getTagsByValue(Collection<String> tags) {
		return tagRepo.findByValueIn(tags);
	}

	public Set<Tag> getTags(Collection<Tag> tags) {
		return getTagsByValue(tags.stream().map(t -> t.getValue()).collect(Collectors.toSet()));
	}

	/**
	 * Search the DB for existing tags. If found returns the tag with id from the
	 * DB, if not found returns the tag as it is. This functionality ensures there
	 * are no duplicated tags.
	 * 
	 * @param tags
	 * @return
	 */
	public Set<Tag> findTags(Collection<Tag> tags) {
		Map<String, Tag> foundTags = getTags(tags).stream().collect(Collectors.toMap(t -> t.getValue(), t -> t));
		return tags.stream().map(t -> foundTags.getOrDefault(t.getValue(), t)).collect(Collectors.toSet());
	}
}
