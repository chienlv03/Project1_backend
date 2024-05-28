package com.example.projecttest.Controller;

import com.example.projecttest.Entity.ClassRoom;
import com.example.projecttest.service.ClassRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/classrooms")
public class ClassroomController {
    @Autowired
    private ClassRoomService classroomService;

    @PostMapping("/create")
    public ClassRoom createClassroom(@RequestBody ClassRoom classroom) {
        return classroomService.createClassroom(classroom);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassRoom> getClassRoomById(@PathVariable Long id) {
        Optional<ClassRoom> classRoom = classroomService.getClassRoomById(id);
        if (classRoom.isPresent()) {
            return ResponseEntity.ok(classRoom.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public List<ClassRoom> getAllClassrooms() {
        return classroomService.getAllClassrooms();
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
    public ResponseEntity<Void> deleteClassRoom(@PathVariable Long id) {
        classroomService.deleteClassRoom(id);
        return ResponseEntity.noContent().build();
    }
}
