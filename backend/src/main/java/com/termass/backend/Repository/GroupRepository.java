package com.termass.backend.Repository;

import com.termass.backend.Entities.TaskGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<TaskGroup, Long> {
    List<TaskGroup> findByCreatorId(String creatorId);
}
