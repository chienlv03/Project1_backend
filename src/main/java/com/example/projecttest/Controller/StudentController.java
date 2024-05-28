package com.example.projecttest.Controller;

import com.example.projecttest.Entity.Student;
import com.example.projecttest.request.StudentInforRequest;
import com.example.projecttest.request.StudentRequest;
import com.example.projecttest.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping("/create/{classroomId}")
    public Student createStudent(@RequestBody Student student, @PathVariable Long classroomId) {
        return studentService.createStudent(student, classroomId);
    }
    @GetMapping("/all")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Optional<Student> student = studentService.getStudentById(id);
        return student.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        try {
            Student updatedStudent = studentService.updateStudent(id, studentDetails);
            return ResponseEntity.ok(updatedStudent);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{studentId}/{classroomId}")
    public ResponseEntity<?> deleteStudentFromClassroom(@PathVariable Long studentId, @PathVariable Long classroomId) {
        studentService.deleteStudentFromClassroom(studentId, classroomId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/classroom/{classroomId}")
    public List<StudentRequest> getStudentsByClassroom(@PathVariable Long classroomId) {
        return studentService.getStudentsByClassroom(classroomId);
    }

    @GetMapping("information/classroom/{classroomId}")
    public List<StudentInforRequest> getStudentsInforByClassroom(@PathVariable Long classroomId) {
        return studentService.getStudentInforByClassroom(classroomId);
    }
}
