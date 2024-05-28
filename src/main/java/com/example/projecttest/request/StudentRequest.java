package com.example.projecttest.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequest {
    private Long studentId;
    private String studentCode;
    private String StudentName;
    private String dob;
    private String email;
    private String className;
    private List<AttendanceRequest> attendanceRecords;
}
