package com.scififics.writershub.data;

import com.scififics.writershub.models.Story;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryRepository extends CrudRepository<Story, Integer> {
}
