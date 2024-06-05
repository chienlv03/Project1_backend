package com.example.projecttest.Controller;

import com.example.projecttest.Entity.ClassRoom;
import com.example.projecttest.service.ClassRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api/classrooms")
public class ClassroomController {
    @Autowired
    private ClassRoomService classroomService;

    @PostMapping("/create/{teacherId}")
    public ClassRoom createClassroom(@RequestBody ClassRoom classroom, @PathVariable Long teacherId) {
        return classroomService.createClassroom(classroom, teacherId);
    }

    @GetMapping("/{teacherId}")
    public List<ClassRoom> getClassroomsByTeacherId(@PathVariable Long teacherId) {
        return classroomService.getClassroomsByTeacherId(teacherId);
    }

//    @GetMapping("/all")
//    @PreAuthorize("hasRole('ADMIN')")
//    public List<ClassRoom> getAllClassrooms() {
//        return classroomService.getAllClassrooms();
//    }

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
