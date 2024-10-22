package com.example.projecttest.Repository;

import com.example.projecttest.Entity.Attendance;
import com.example.projecttest.Entity.ClassRoom;
import com.example.projecttest.Entity.Student;
import com.example.projecttest.Entity.StudentClassroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByStudentClassroomAndAttendanceTime(StudentClassroom studentClassroom, String attendanceTime);

    @Query("SELECT DISTINCT a.attendanceTime FROM Attendance a WHERE a.studentClassroom.classroom.id = :classroomId")
    List<String> findDistinctAttendanceTimesByClassroomId(@Param("classroomId") Long classroomId);
}
