package com.example.projecttest.request;

import com.example.projecttest.Entity.Enum.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceRequest {
    private String attendanceTime;
    private AttendanceStatus status;
}
