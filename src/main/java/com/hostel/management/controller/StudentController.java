package com.hostel.management.controller;

import com.hostel.management.entity.Student;
import com.hostel.management.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin("http://localhost:5173")
//@CrossOrigin(
//        origins = "http://localhost:5173",
//        allowedHeaders = "*",
//        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS}
//)
//@CrossOrigin("*")

public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping(value = "/room/{roomId}", consumes = "multipart/form-data")
    public Student createStudent(
            @PathVariable Long roomId,
            @ModelAttribute Student student,
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("identityDocument") MultipartFile identityDocument
    ) throws IOException {
        return studentService.createStudent(student, roomId, photo, identityDocument);
    }

    @GetMapping
    public List<Student> getAllStudents(){
        return studentService.getAllStudent();
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student student){
        return studentService.updateStudent(id, student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id){
        studentService.deleteStudent(id);
    }

}
