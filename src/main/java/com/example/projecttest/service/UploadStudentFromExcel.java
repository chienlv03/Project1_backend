package com.example.projecttest.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadStudentFromExcel {
    void saveStudentsFromExcel(MultipartFile file, Long classroomId) throws IOException;
}
