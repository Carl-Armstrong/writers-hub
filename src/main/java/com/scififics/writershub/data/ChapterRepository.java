package com.scififics.writershub.data;

import com.scififics.writershub.models.Chapter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChapterRepository extends CrudRepository<Chapter, Integer> {
}
