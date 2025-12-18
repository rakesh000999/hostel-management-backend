package com.hostel.management.service;

import com.hostel.management.entity.Student;

import java.util.List;

public interface StudentService {
    Student createStudent(Student student, Long roomId);
    List<Student> getAllStudent();
    Student getStudentById(Long id);
    Student updateStudent(Long id, Student student);
    void deleteStudent(Long id);
    List<Student> getStudentsByRoomId(Long roomId);
}
