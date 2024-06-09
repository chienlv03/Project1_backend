package com.example.projecttest.service.serviceImpl;

import com.example.projecttest.Entity.ClassRoom;
import com.example.projecttest.Entity.StudentClassroom;
import com.example.projecttest.Entity.User;
import com.example.projecttest.Repository.*;
import com.example.projecttest.service.ClassRoomService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClassRoomServiceImpl implements ClassRoomService {

    @Autowired
    private ClassRoomRepository classroomRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentClassroomRepository studentClassroomRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ClassRoom createClassroom(ClassRoom classroom, Long teacherId) {

        User user = userRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + teacherId));
        classroom.setTeacher(user);
        return classroomRepository.save(classroom);
    }

    @Override
    public List<ClassRoom> getClassroomsByTeacherId(Long teacherId) {
        return classroomRepository.findAllByTeacherId(teacherId);
    }

//    public List<ClassRoom> getAllClassrooms() {
//        return classroomRepository.findAll();
//    }

    public ClassRoom updateClassRoom(Long id, ClassRoom classRoomDetails) {
        Optional<ClassRoom> classRoomOptional = classroomRepository.findById(id);
        if (classRoomOptional.isPresent()) {
            ClassRoom classRoom = classRoomOptional.get();
            classRoom.setName(classRoomDetails.getName());
            classRoom.setClassCode(classRoomDetails.getClassCode());
            classRoom.setStartTime(classRoomDetails.getStartTime());
            return classroomRepository.save(classRoom);
        } else {
            throw new RuntimeException("ClassRoom not found with id " + id);
        }
    }

    @Transactional
    public void deleteClassRoom(Long id) {
        // Find all StudentClassroom records related to the classroom
        List<StudentClassroom> studentClassrooms = studentClassroomRepository.findByClassroomId(id);
        attendanceRepository.deleteAllAttendanceByStudentClassroomClassroomId(id);
        studentClassroomRepository.deleteByClassroomId(id);
        studentRepository.deleteAll(studentClassrooms.stream().map(StudentClassroom::getStudent).collect(Collectors.toList()));
        // Finally, delete the classroom
        classroomRepository.deleteById(id);

    }
}
