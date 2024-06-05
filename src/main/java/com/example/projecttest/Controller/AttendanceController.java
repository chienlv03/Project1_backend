package com.example.projecttest.Controller;

import com.example.projecttest.Entity.Attendance;
import com.example.projecttest.request.CreateAttendanceRequest;
import com.example.projecttest.request.UpdateAttendanceRequest;
import com.example.projecttest.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/classroom/{classroomId}")
    public ResponseEntity<List<Attendance>> createAttendanceRecords(@PathVariable Long classroomId, @RequestBody CreateAttendanceRequest request) {
        List<Attendance> attendances = attendanceService.createAttendanceRecords(classroomId, request.getAttendanceTime());
        return ResponseEntity.ok(attendances);
    }


    @PutMapping("/student/{studentId}/classroom/{classroomId}")
    public ResponseEntity<Attendance> updateAttendanceStatus(@PathVariable Long studentId,
                                                             @PathVariable Long classroomId,
                                                             @RequestBody UpdateAttendanceRequest updateAttendanceRequest) {
        Attendance attendance = attendanceService.updateAttendanceStatus(
                studentId,
                classroomId,
                updateAttendanceRequest.getAttendanceTime(),
                updateAttendanceRequest.getIsAbsent(),
                updateAttendanceRequest.getIsExcused()
        );
        return ResponseEntity.ok(attendance);
    }

    @GetMapping("/classroom/{classroomId}/times")
    public ResponseEntity<List<String>> getDistinctAttendanceTimesByClassroomId(@PathVariable Long classroomId) {
        List<String> attendanceTimes = attendanceService.getDistinctAttendanceTimesByClassroomId(classroomId);
        return ResponseEntity.ok(attendanceTimes);
    }

}
