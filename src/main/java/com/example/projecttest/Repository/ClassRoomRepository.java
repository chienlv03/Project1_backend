package com.example.projecttest.Repository;

import com.example.projecttest.Entity.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassRoomRepository extends JpaRepository<ClassRoom, Long> {
    List<ClassRoom> findAllByTeacherId(Long teacherId);
}
