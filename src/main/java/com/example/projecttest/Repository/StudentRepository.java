package com.example.projecttest.Repository;

import com.example.projecttest.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
