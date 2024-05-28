package com.example.projecttest.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class UpdateAttendanceRequest {
    private String attendanceTime;
    private Boolean isAbsent;
    private Boolean isExcused;
}
