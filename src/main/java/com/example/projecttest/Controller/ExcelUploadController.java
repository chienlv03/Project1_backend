package com.example.projecttest.Controller;

import com.example.projecttest.service.serviceImpl.UploadStudentFromExcelImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/excel")
public class ExcelUploadController {

    @Autowired
    private UploadStudentFromExcelImpl excelService;

    @PostMapping("/upload/classroom/{classroomId}")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable Long classroomId) {
        try {
            excelService.saveStudentsFromExcel(file, classroomId);
            return ResponseEntity.ok("File uploaded and students saved successfully");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to process file: " + e.getMessage());
        }
    }
}
