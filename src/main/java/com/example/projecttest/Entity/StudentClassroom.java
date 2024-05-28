package com.example.projecttest.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class StudentClassroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int unexcusedAbsenceCount = 0;
    private int excusedAbsenceCount = 0;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    @JsonBackReference("student_classroom")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "classroom_id", nullable = false)
    @JsonBackReference("student_classroom_class")
    private ClassRoom classroom;

    @OneToMany(mappedBy = "studentClassroom", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("student_classroom_attendances")
    private List<Attendance> attendances;
}
