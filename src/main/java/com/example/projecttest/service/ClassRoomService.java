package com.example.projecttest.service;

import com.example.projecttest.Entity.ClassRoom;

import java.util.List;
import java.util.Optional;

public interface ClassRoomService {
    ClassRoom createClassroom(ClassRoom classroom, Long teacherId);
    List<ClassRoom> getClassroomsByTeacherId(Long teacherId);

//    List<ClassRoom> getAllClassrooms();
    ClassRoom updateClassRoom(Long id, ClassRoom classRoomDetails);
    void deleteClassRoom(Long id);
}
