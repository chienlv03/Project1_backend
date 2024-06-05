package com.example.projecttest.service;

import com.example.projecttest.Entity.Student;
import com.example.projecttest.request.StudentInforRequest;
import com.example.projecttest.request.StudentRequest;

import java.util.List;
import java.util.Optional;


public interface StudentService {
    Student createStudent(Student student, Long classroomId);

//    List<Student> getAllStudents();

//    Optional<Student> getStudentById(Long id);

    Student updateStudent(Long id, Student studentDetails);

    List<StudentRequest> getStudentsByClassroom(Long classroomId);

    List<StudentInforRequest> getStudentInforByClassroom(Long classroomId);

    void deleteStudentFromClassroom(Long studentId, Long classroomId);
}
