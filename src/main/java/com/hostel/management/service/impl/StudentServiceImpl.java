package com.hostel.management.service.impl;

import com.hostel.management.entity.Room;
import com.hostel.management.entity.Student;
import com.hostel.management.exception.ResourceNotFoundException;
import com.hostel.management.repository.RoomRepository;
import com.hostel.management.repository.StudentRepository;
import com.hostel.management.security.AESUtil;
import com.hostel.management.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.SecretKey;
import java.io.File;
import java.nio.file.Files;
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
                                 MultipartFile identityDocumentPath) throws Exception {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        if (room.getOccupiedCount() >= room.getCapacity()) {
            throw new RuntimeException("Room is full");
        }

        student.setRoom(room);
        Student savedStudent = studentRepository.save(student);

        room.setOccupiedCount(room.getOccupiedCount() + 1);
        roomRepository.save(room);

        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        // photo encryption
        if (photoPath != null && !photoPath.isEmpty()) {

            byte[] originalBytes = photoPath.getBytes();

            SecretKey aesKey = AESUtil.generateKey();
            byte[] encryptedBytes = AESUtil.encrypt(originalBytes, aesKey);

            File encryptedFile = new File(uploadDir,
                    savedStudent.getId() + "_photo.enc");

            Files.write(encryptedFile.toPath(), encryptedBytes);

            savedStudent.setPhotoPath(encryptedFile.getAbsolutePath());
            savedStudent.setPhotoAesKey(AESUtil.keyToBytes(aesKey));

            // store MIME type
            savedStudent.setPhotoContentType(photoPath.getContentType());
        }

        if (identityDocumentPath != null && !identityDocumentPath.isEmpty()) {

            byte[] originalBytes = identityDocumentPath.getBytes();

            SecretKey aesKey = AESUtil.generateKey();
            byte[] encryptedBytes = AESUtil.encrypt(originalBytes, aesKey);

            File encryptedFile = new File(uploadDir,
                    savedStudent.getId() + "_identity.enc");

            Files.write(encryptedFile.toPath(), encryptedBytes);

            savedStudent.setIdentityDocumentPath(encryptedFile.getAbsolutePath());
            savedStudent.setIdentityAesKey(AESUtil.keyToBytes(aesKey));

            // store MIME type
            savedStudent.setIdentityContentType(identityDocumentPath.getContentType());
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
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
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

        if (room != null) {
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
