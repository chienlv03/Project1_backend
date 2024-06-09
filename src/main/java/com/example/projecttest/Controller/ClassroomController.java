package com.example.projecttest.Controller;

import com.example.projecttest.Entity.ClassRoom;
import com.example.projecttest.Repository.ClassRoomRepository;
import com.example.projecttest.payload.response.MessageResponse;
import com.example.projecttest.service.ClassRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api/classrooms")
public class ClassroomController {
    @Autowired
    private ClassRoomService classroomService;

    @Autowired
    private ClassRoomRepository classroomRepository;

    @PostMapping("/create/{teacherId}")
    public ResponseEntity<?> createClassroom(@RequestBody ClassRoom classroom, @PathVariable Long teacherId) {
        if (classroomRepository.existsByClassCode(classroom.getClassCode())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Mã lớp đã tồn tại"));
        }
        try {
            ClassRoom createdClassRoom = classroomService.createClassroom(classroom, teacherId);
            return ResponseEntity.ok(createdClassRoom);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new MessageResponse("An error occurred while creating the classroom"));
        }
    }

    @GetMapping("/get/{teacherId}")
    public List<ClassRoom> getClassroomsByTeacherId(@PathVariable Long teacherId) {
        return classroomService.getClassroomsByTeacherId(teacherId);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ClassRoom> updateClassRoom(@PathVariable Long id, @RequestBody ClassRoom classRoomDetails) {
        try {
            ClassRoom updatedClassRoom = classroomService.updateClassRoom(id, classRoomDetails);
            return ResponseEntity.ok(updatedClassRoom);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> deleteClassroomAndStudents(@PathVariable Long id) {
        classroomService.deleteClassRoom(id);
        return ResponseEntity.noContent().build();
    }
}
