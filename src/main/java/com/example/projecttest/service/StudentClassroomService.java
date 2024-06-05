package com.example.projecttest.service;

import com.example.projecttest.Entity.ClassRoom;
import com.example.projecttest.Entity.Student;
import com.example.projecttest.Entity.StudentClassroom;
import com.example.projecttest.request.StudentAbsenceDTO;

import java.util.List;

public interface StudentClassroomService {
//    StudentClassroom enrollStudentInClassroom(Long studentId, Long classroomId);

//    List<ClassRoom> getClassroomsForStudent(Long studentId);

//    List<Student> getStudentsInClassroom(Long classroomId);

    // Lấy ra số ần vắng của mỗi học sinh trong 1 lớp
    List<StudentAbsenceDTO> getAbsencesForClassroom(Long classroomId);

    // Xóa học sinh khỏi lớp
//    void removeStudentFromClassroom(Long studentId, Long classroomId);
}
