package com.example.projecttest.request;

import com.example.projecttest.Entity.ClassRoom;
import com.example.projecttest.Entity.Student;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnrollmentRequest {
    private Long studentId;
    private Long classroomId;
}
