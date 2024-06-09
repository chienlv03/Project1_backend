package com.example.projecttest.Repository;

import com.example.projecttest.Entity.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClassRoomRepository extends JpaRepository<ClassRoom, Long> {
    List<ClassRoom> findAllByTeacherId(Long teacherId);

    boolean existsByClassCode(String classCode);
}
