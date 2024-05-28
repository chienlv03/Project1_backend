package com.example.projecttest.Controller;

import com.example.projecttest.Entity.ClassRoom;
import com.example.projecttest.Entity.Student;
import com.example.projecttest.Entity.StudentClassroom;
import com.example.projecttest.request.EnrollmentRequest;
import com.example.projecttest.request.StudentAbsenceDTO;
import com.example.projecttest.service.StudentClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/enrollments")
public class StudentClassroomController {
    @Autowired
    private StudentClassroomService studentClassroomService;

    // Thêm sinh viên vào lớp
    @PostMapping
    public ResponseEntity<StudentClassroom> enrollStudentInClassroom(@RequestBody EnrollmentRequest enrollmentRequest) {
        StudentClassroom studentClassroom = studentClassroomService.enrollStudentInClassroom(
                enrollmentRequest.getStudentId(), enrollmentRequest.getClassroomId());
        return new ResponseEntity<>(studentClassroom, HttpStatus.CREATED);
    }

    // Lấy tất cả sinh viên của 1 lớp
    @GetMapping("/classrooms/{classroomId}/students")
    public ResponseEntity<List<Student>> getStudentsInClassroom(@PathVariable Long classroomId) {
        List<Student> students = studentClassroomService.getStudentsInClassroom(classroomId);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // Lấy tất cả lớp của 1 sinh viên
    @GetMapping("/students/{studentId}/classrooms")
    public ResponseEntity<List<ClassRoom>> getClassroomsForStudent(@PathVariable Long studentId) {
        List<ClassRoom> classrooms = studentClassroomService.getClassroomsForStudent(studentId);
        return new ResponseEntity<>(classrooms, HttpStatus.OK);
    }

    // lấy số lần vắng của tất cả học sinh trong 1 lớp
    @GetMapping("/classroom/{classroomId}/absences")
    public List<StudentAbsenceDTO> getAbsencesForClassroom(@PathVariable Long classroomId) {
        return studentClassroomService.getAbsencesForClassroom(classroomId);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeStudentFromClassroom(@RequestParam Long studentId, @RequestParam Long classroomId) {
        studentClassroomService.removeStudentFromClassroom(studentId, classroomId);
        return ResponseEntity.noContent().build();
    }
}


