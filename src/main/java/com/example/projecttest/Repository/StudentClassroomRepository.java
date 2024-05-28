package com.example.projecttest.Repository;

import com.example.projecttest.Entity.StudentClassroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface StudentClassroomRepository extends JpaRepository<StudentClassroom, Long> {
    List<StudentClassroom> findByClassroomId(Long classroomId);
    List<StudentClassroom> findByStudentId(Long studentId);

    Optional<StudentClassroom> findByStudentIdAndClassroomId(Long studentId, Long classroomId);

    @Transactional
    void deleteByClassroomId(Long classroomId);


}
