package com.example.projecttest.service.serviceImpl;

import com.example.projecttest.Entity.ClassRoom;
import com.example.projecttest.Entity.Student;
import com.example.projecttest.Entity.StudentClassroom;
import com.example.projecttest.Repository.ClassRoomRepository;
import com.example.projecttest.Repository.StudentClassroomRepository;
import com.example.projecttest.Repository.StudentRepository;
import com.example.projecttest.request.StudentAbsenceDTO;
import com.example.projecttest.service.StudentClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentClassroomServiceImpl implements StudentClassroomService {
    @Autowired
    private StudentClassroomRepository studentClassroomRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassRoomRepository classroomRepository;

    // Thêm stuednt vào lớp
//    public StudentClassroom enrollStudentInClassroom(Long studentId, Long classroomId) {
//        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));
//        ClassRoom classroom = classroomRepository.findById(classroomId).orElseThrow(() -> new RuntimeException("Classroom not found"));
//
//        StudentClassroom studentClassroom = new StudentClassroom();
//        studentClassroom.setStudent(student);
//        studentClassroom.setClassroom(classroom);
//
//        return studentClassroomRepository.save(studentClassroom);
//    }

    // Lấy tất cả học sinh trong 1 lớp
//    public List<Student> getStudentsInClassroom(Long classroomId) {
//        return studentClassroomRepository.findByClassroomId(classroomId)
//                .stream()
//                .map(StudentClassroom::getStudent)
//                .collect(Collectors.toList());
//    }

    // Lấy tất cả lớp của 1 học sinh
//    public List<ClassRoom> getClassroomsForStudent(Long studentId) {
//        return studentClassroomRepository.findByStudentId(studentId)
//                .stream()
//                .map(StudentClassroom::getClassroom)
//                .collect(Collectors.toList());
//    }

    // Lấy ra số ần vắng của mỗi học sinh trong 1 lớp
    @Override
    public List<StudentAbsenceDTO> getAbsencesForClassroom(Long classroomId) {
        List<StudentClassroom> studentClassrooms = studentClassroomRepository.findByClassroomId(classroomId);
        return studentClassrooms.stream()
                .map(sc -> new StudentAbsenceDTO(  // Assuming Student entity has a getName() method
                        sc.getStudent().getId(),
                        sc.getUnexcusedAbsenceCount(),
                        sc.getExcusedAbsenceCount()))
                .collect(Collectors.toList());
    }

    // Xóa học sinh khỏi lớp
//    @Override
//    public void removeStudentFromClassroom(Long studentId, Long classroomId) {
//        StudentClassroom studentClassroom = studentClassroomRepository.findByStudentIdAndClassroomId(studentId, classroomId)
//                .orElseThrow(() -> new RuntimeException("Enrollment record not found"));
//        studentClassroomRepository.delete(studentClassroom);
//    }
}
