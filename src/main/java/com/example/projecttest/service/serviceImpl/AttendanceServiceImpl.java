package com.example.projecttest.service.serviceImpl;

import com.example.projecttest.Entity.Attendance;
import com.example.projecttest.Entity.Enum.AttendanceStatus;
import com.example.projecttest.Entity.StudentClassroom;
import com.example.projecttest.Repository.AttendanceRepository;
import com.example.projecttest.Repository.StudentClassroomRepository;
import com.example.projecttest.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {
    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private StudentClassroomRepository studentClassroomRepository;
    @Override
    public List<Attendance> createAttendanceRecords(Long classroomId, String attendanceTime) {
        List<StudentClassroom> studentClassrooms = studentClassroomRepository.findByClassroomId(classroomId);
        List<Attendance> attendances = new ArrayList<>();

        for (StudentClassroom studentClassroom : studentClassrooms) {
            Attendance attendance = new Attendance();
            attendance.setStudentClassroom(studentClassroom);
            attendance.setAttendanceTime(attendanceTime);
            attendance.setStatus(AttendanceStatus.PRESENT); // Default status
            attendances.add(attendance);
        }

        return attendanceRepository.saveAll(attendances);
    }

    @Override
    public Attendance updateAttendanceStatus(Long studentId, Long classroomId, String attendanceTime, Boolean isAbsent, Boolean isExcused) {
        StudentClassroom studentClassroom = studentClassroomRepository.findByStudentIdAndClassroomId(studentId, classroomId)
                .orElseThrow(() -> new RuntimeException("StudentClassroom not found for studentId: " + studentId + " and classroomId: " + classroomId));

        Attendance attendance = attendanceRepository.findByStudentClassroomAndAttendanceTime(studentClassroom, attendanceTime)
                .orElseThrow(() ->  new RuntimeException("Attendance not found for studentId: " + studentId + " and classroomId: " + classroomId + " at " + attendanceTime));

        AttendanceStatus oldStatus = attendance.getStatus();
        AttendanceStatus newStatus = calculateStatus(isAbsent, isExcused);

        attendance.setStatus(newStatus);
        attendanceRepository.save(attendance);

        updateAbsentCounts(studentId, classroomId, oldStatus, newStatus);

        return attendance;
    }


    private AttendanceStatus calculateStatus(Boolean isAbsent, Boolean isExcused) {
        if (isAbsent != null && isAbsent) {
            return isExcused != null && isExcused ? AttendanceStatus.ABSENT_EXCUSED : AttendanceStatus.ABSENT_UNEXCUSED;
        } else {
            return AttendanceStatus.PRESENT;
        }
    }

    private void updateAbsentCounts(Long studentId, Long classroomId, AttendanceStatus oldStatus, AttendanceStatus newStatus) {
        StudentClassroom studentClassroom = studentClassroomRepository.findByStudentIdAndClassroomId(studentId, classroomId)
                .orElseThrow(() -> new RuntimeException("StudentClassroom not found for studentId: " + studentId + " and classroomId: " + classroomId));

        if (oldStatus == AttendanceStatus.ABSENT_UNEXCUSED) {
            studentClassroom.setUnexcusedAbsenceCount(studentClassroom.getUnexcusedAbsenceCount() - 1);
        } else if (oldStatus == AttendanceStatus.ABSENT_EXCUSED) {
            studentClassroom.setExcusedAbsenceCount(studentClassroom.getExcusedAbsenceCount() - 1);
            if (studentClassroom.getExcusedAbsenceCount() % 2 != 0) {
                studentClassroom.setUnexcusedAbsenceCount(studentClassroom.getUnexcusedAbsenceCount() - 1);
            }
        }

        if (newStatus == AttendanceStatus.ABSENT_UNEXCUSED) {
            studentClassroom.setUnexcusedAbsenceCount(studentClassroom.getUnexcusedAbsenceCount() + 1);
        } else if (newStatus == AttendanceStatus.ABSENT_EXCUSED) {
            studentClassroom.setExcusedAbsenceCount(studentClassroom.getExcusedAbsenceCount() + 1);
            if (studentClassroom.getExcusedAbsenceCount() % 2 == 0) {
                studentClassroom.setUnexcusedAbsenceCount(studentClassroom.getUnexcusedAbsenceCount() + 1);
            }
        }

        studentClassroomRepository.save(studentClassroom);
    }

    @Override
    public List<String> getDistinctAttendanceTimesByClassroomId(Long classroomId) {
        return attendanceRepository.findDistinctAttendanceTimesByClassroomId(classroomId);
    }

}
