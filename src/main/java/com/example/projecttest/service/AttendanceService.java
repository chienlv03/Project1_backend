package com.example.projecttest.service;


import com.example.projecttest.Entity.Attendance;
import java.util.List;

public interface AttendanceService {

    List<Attendance> createAttendanceRecords(Long classroomId, String attendanceTime);


    Attendance updateAttendanceStatus(Long studentId, Long classroomId, String attendanceTime, Boolean isAbsent, Boolean isExcused);

    List<String> getDistinctAttendanceTimesByClassroomId(Long classroomId);
}
