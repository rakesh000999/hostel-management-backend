package com.hostel.management.service;

import com.hostel.management.entity.Student;
import jakarta.mail.Multipart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StudentService {
    Student createStudent(Student student, Long roomId, MultipartFile photo, MultipartFile identityDocument) throws IOException;
    List<Student> getAllStudent();
    Student getStudentById(Long id);
    Student updateStudent(Long id, Student student);
    void deleteStudent(Long id);
    List<Student> getStudentsByRoomId(Long roomId);
}
