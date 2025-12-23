package com.hostel.management.service.impl;

import com.hostel.management.entity.Room;
import com.hostel.management.entity.Student;
import com.hostel.management.exception.ResourceNotFoundException;
import com.hostel.management.repository.RoomRepository;
import com.hostel.management.repository.StudentRepository;
import com.hostel.management.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public Student createStudent(Student student, Long roomId,
                                 MultipartFile photoPath,
                                 MultipartFile identityDocumentPath) throws IOException {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + roomId));

        if (room.getOccupiedCount() >= room.getCapacity()) {
            throw new RuntimeException("Room is already full.");
        }

        student.setRoom(room);

        // Save student FIRST to generate ID
        Student savedStudent = studentRepository.save(student);

        room.setOccupiedCount(room.getOccupiedCount() + 1);
        roomRepository.save(room);

        File uploadDirectory = new File(uploadDir);
        if (!uploadDirectory.exists()) {
            uploadDirectory.mkdirs();
        }

        if (photoPath != null && !photoPath.isEmpty()) {
            String photoFileName = savedStudent.getId() + "_photo_" + photoPath.getOriginalFilename();
            File photoFile = new File(uploadDirectory, photoFileName);
            photoPath.transferTo(photoFile);
            savedStudent.setPhotoPath(photoFile.getAbsolutePath());
        }

        if (identityDocumentPath != null && !identityDocumentPath.isEmpty()) {
            String docFileName = savedStudent.getId() + "_id_" + identityDocumentPath.getOriginalFilename();
            File docFile = new File(uploadDirectory, docFileName);
            identityDocumentPath.transferTo(docFile);
            savedStudent.setIdentityDocumentPath(docFile.getAbsolutePath());
        }

        return studentRepository.save(savedStudent);
    }

    @Override
    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id -" + id));
    }

    @Override
    public Student updateStudent(Long id, Student updateStudent) {
        Student student = getStudentById(id);
        student.setName(updateStudent.getName());
        student.setEmail(updateStudent.getEmail());
        student.setAddress(updateStudent.getAddress());
        student.setGender(updateStudent.getGender());
        student.setPhone(updateStudent.getPhone());
        student.setGuardianName(updateStudent.getGuardianName());
        student.setGuardianContact(updateStudent.getGuardianContact());
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {

        Student student = getStudentById(id);
        Room room = student.getRoom();

        // Decrease the room occupied count
        if(room != null){
            room.setOccupiedCount(room.getOccupiedCount() - 1);
            roomRepository.save(room);
        }

        studentRepository.deleteById(id);
    }

    @Override
    public List<Student> getStudentsByRoomId(Long roomId) {
        return studentRepository.findByRoomId(roomId);
    }
}
