package com.example.projecttest.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentInforRequest {
    private Long studentId;
    private String studentCode;
    private String StudentName;
    private String dob;
    private String gender;
    private String email;
    private String className;
    private String startTime;
}
