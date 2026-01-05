package com.hostel.management.controller;

import com.hostel.management.entity.Student;
import com.hostel.management.repository.StudentRepository;
import com.hostel.management.security.AESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.io.File;
import java.nio.file.Files;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private StudentRepository studentRepository;

    /**
     * View decrypted student documents
     */

    @GetMapping("/student/{studentId}/{type}")
    public ResponseEntity<ByteArrayResource> viewStudentFile(
            @PathVariable Long studentId,
            @PathVariable String type
    ) throws Exception {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        String encryptedFilePath;
        byte[] encryptedKey;
        String contentType;

        if ("photo".equalsIgnoreCase(type)) {
            encryptedFilePath = student.getPhotoPath();
            encryptedKey = student.getPhotoAesKey();
            contentType = student.getPhotoContentType();
        } else if ("identity".equalsIgnoreCase(type)) {
            encryptedFilePath = student.getIdentityDocumentPath();
            encryptedKey = student.getIdentityAesKey();
            contentType = student.getIdentityContentType();
        } else {
            throw new RuntimeException("Invalid file type");
        }

        if (encryptedKey == null || encryptedKey.length == 0) {
            throw new RuntimeException("Encryption key missing");
        }

        byte[] encryptedBytes = Files.readAllBytes(new File(encryptedFilePath).toPath());

        SecretKey aesKey = AESUtil.bytesToKey(encryptedKey);
        byte[] decryptedBytes = AESUtil.decrypt(encryptedBytes, aesKey);

        ByteArrayResource resource = new ByteArrayResource(decryptedBytes);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                .contentType(MediaType.parseMediaType(contentType))
                .contentLength(decryptedBytes.length)
                .body(resource);
    }

}
