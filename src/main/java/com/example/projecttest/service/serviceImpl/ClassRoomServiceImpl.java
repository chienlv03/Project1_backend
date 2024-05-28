package com.example.projecttest.service.serviceImpl;

import com.example.projecttest.Entity.ClassRoom;
import com.example.projecttest.Repository.ClassRoomRepository;
import com.example.projecttest.Repository.StudentClassroomRepository;
import com.example.projecttest.service.ClassRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassRoomServiceImpl implements ClassRoomService {

    @Autowired
    private ClassRoomRepository classroomRepository;

    @Autowired
    private StudentClassroomRepository studentClassroomRepository;

    public ClassRoom createClassroom(ClassRoom classroom) {
        return classroomRepository.save(classroom);
    }

    public Optional<ClassRoom> getClassRoomById(Long id) {
        return classroomRepository.findById(id);
    }

    public List<ClassRoom> getAllClassrooms() {
        return classroomRepository.findAll();
    }

    public ClassRoom updateClassRoom(Long id, ClassRoom classRoomDetails) {
        Optional<ClassRoom> classRoomOptional = classroomRepository.findById(id);
        if (classRoomOptional.isPresent()) {
            ClassRoom classRoom = classRoomOptional.get();
            classRoom.setName(classRoomDetails.getName());
            classRoom.setClassCode(classRoomDetails.getClassCode());
            classRoom.setStartTime(classRoomDetails.getStartTime());
            classRoom.setCapacity(classRoomDetails.getCapacity());
            return classroomRepository.save(classRoom);
        } else {
            throw new RuntimeException("ClassRoom not found with id " + id);
        }
    }

    public void deleteClassRoom(Long id) {
        Optional<ClassRoom> classRoomOptional = classroomRepository.findById(id);
        if (classRoomOptional.isPresent()) {
            studentClassroomRepository.deleteByClassroomId(id);
            classroomRepository.delete(classRoomOptional.get());
        } else {
            throw new RuntimeException("ClassRoom not found with id " + id);
        }
    }
}
