package com.example.projecttest.service.serviceImpl;

import com.example.projecttest.Entity.Student;
import com.example.projecttest.Entity.StudentClassroom;
import com.example.projecttest.Repository.StudentClassroomRepository;
import com.example.projecttest.Repository.StudentRepository;
import com.example.projecttest.Repository.ClassRoomRepository;
import com.example.projecttest.service.UploadStudentFromExcel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

@Service
public class UploadStudentFromExcelImpl implements UploadStudentFromExcel {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentClassroomRepository studentClassroomRepository;

    @Autowired
    private ClassRoomRepository classRoomRepository;

    @Override
    public void saveStudentsFromExcel(MultipartFile file, Long classroomId) throws IOException {
        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            while (rows.hasNext()) {
                Row currentRow = rows.next();
                if (currentRow.getRowNum() == 0) { // skip header row
                    continue;
                }

                Student student = new Student();
                student.setStudentCode(currentRow.getCell(0).getStringCellValue());
                student.setStudentName(currentRow.getCell(1).getStringCellValue());
                student.setDob(currentRow.getCell(2).getStringCellValue());
                student.setGender(currentRow.getCell(3).getStringCellValue());
                student.setEmail(currentRow.getCell(4).getStringCellValue());

                Student savedStudent = studentRepository.save(student);

                StudentClassroom studentClassroom = new StudentClassroom();
                studentClassroom.setStudent(savedStudent);
                studentClassroom.setClassroom(classRoomRepository.findById(classroomId).orElseThrow(() -> new RuntimeException("Classroom not found")));
                studentClassroomRepository.save(studentClassroom);
            }
        }
    }
}
