package com.scififics.writershub.data;

import com.scififics.writershub.models.World;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorldRepository extends CrudRepository<World, Integer> {
}
