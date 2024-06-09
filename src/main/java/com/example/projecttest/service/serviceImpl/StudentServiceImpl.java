package com.example.projecttest.service.serviceImpl;

import com.example.projecttest.Entity.Attendance;
import com.example.projecttest.Entity.ClassRoom;
import com.example.projecttest.Entity.Student;
import com.example.projecttest.Entity.StudentClassroom;
import com.example.projecttest.Repository.AttendanceRepository;
import com.example.projecttest.Repository.ClassRoomRepository;
import com.example.projecttest.Repository.StudentClassroomRepository;
import com.example.projecttest.Repository.StudentRepository;
import com.example.projecttest.request.AttendanceRequest;
import com.example.projecttest.request.StudentInforRequest;
import com.example.projecttest.request.StudentRequest;
import com.example.projecttest.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassRoomRepository classroomRepository;

    @Autowired
    private StudentClassroomRepository studentClassroomRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Override
    public Student createStudent(Student student, Long classroomId) {
        // Lưu học sinh vào database
        Student savedStudent = studentRepository.save(student);

        // Lấy lớp học từ database
        ClassRoom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new RuntimeException("Classroom not found with id: " + classroomId));

        // Thêm học sinh vào lớp học
        StudentClassroom studentClassroom = new StudentClassroom();
        studentClassroom.setStudent(savedStudent);
        studentClassroom.setClassroom(classroom);
        studentClassroom.setUnexcusedAbsenceCount(0);
        studentClassroom.setExcusedAbsenceCount(0);
        studentClassroomRepository.save(studentClassroom);

        return savedStudent;
    }


    public Student updateStudent(Long id, Student studentDetails) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            student.setStudentCode(studentDetails.getStudentCode());
            student.setStudentName(studentDetails.getStudentName());
            student.setDob(studentDetails.getDob());
            student.setGender(studentDetails.getGender());
            student.setEmail(studentDetails.getEmail());
            return studentRepository.save(student);
        } else {
            throw new RuntimeException("Student not found with id " + id);
        }
    }

    @Override
    public List<StudentRequest> getStudentsByClassroom(Long classroomId) {
        ClassRoom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new RuntimeException("Classroom not found with id: " + classroomId));

        List<StudentClassroom> studentClassrooms = studentClassroomRepository.findByClassroomId(classroomId);

        return studentClassrooms.stream().map(sc -> {
            StudentRequest studentRequest = new StudentRequest();
            studentRequest.setStudentId(sc.getStudent().getId());
            studentRequest.setStudentCode(sc.getStudent().getStudentCode());
            studentRequest.setStudentName(sc.getStudent().getStudentName());
            studentRequest.setDob(sc.getStudent().getDob());
            studentRequest.setEmail(sc.getStudent().getEmail());
            studentRequest.setClassName(classroom.getName());

            List<AttendanceRequest> attendanceRequests = sc.getAttendances().stream().map(attendance -> {
                AttendanceRequest attendanceRequest = new AttendanceRequest();
                attendanceRequest.setStatus(attendance.getStatus());
                attendanceRequest.setAttendanceTime(attendance.getAttendanceTime());
                // Thêm các field khác nếu cần
                return attendanceRequest;
            }).collect(Collectors.toList());

            studentRequest.setAttendanceRecords(attendanceRequests);
            return studentRequest;
        }).collect(Collectors.toList());
    }

    @Override
    public List<StudentInforRequest> getStudentInforByClassroom(Long classroomId) {
        ClassRoom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new RuntimeException("Classroom not found with id: " + classroomId));

        List<StudentClassroom> studentClassrooms = studentClassroomRepository.findByClassroomId(classroomId);

        return studentClassrooms.stream().map(sc -> {
            StudentInforRequest studentInforRequest = new StudentInforRequest();
            studentInforRequest.setStudentId(sc.getStudent().getId());
            studentInforRequest.setStudentCode(sc.getStudent().getStudentCode());
            studentInforRequest.setStudentName(sc.getStudent().getStudentName());
            studentInforRequest.setDob(sc.getStudent().getDob());
            studentInforRequest.setGender(sc.getStudent().getGender());
            studentInforRequest.setEmail(sc.getStudent().getEmail());
            studentInforRequest.setClassName(classroom.getName());
            studentInforRequest.setStartTime(classroom.getStartTime());
            return studentInforRequest;
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteStudentFromClassroom(Long studentId, Long classroomId) {
        // Tìm học sinh trong lớp học
        StudentClassroom studentClassroom = studentClassroomRepository.findByStudentIdAndClassroomId(studentId, classroomId)
                .orElseThrow(() -> new RuntimeException("StudentClassroom not found for studentId: " + studentId + " and classroomId: " + classroomId));

        // Xóa bản ghi Attendance
        List<Attendance> attendances = attendanceRepository.findByStudentClassroom_Student_Id(studentId);

        attendanceRepository.deleteAll(attendances);
        // Xóa bản ghi StudentClassroom
        studentClassroomRepository.delete(studentClassroom);

        // Xóa học sinh khỏi database
        studentRepository.deleteById(studentId);
    }
}
