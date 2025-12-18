package com.hostel.management.service.impl;

import com.hostel.management.entity.Room;
import com.hostel.management.entity.Student;
import com.hostel.management.exception.ResourceNotFoundException;
import com.hostel.management.repository.RoomRepository;
import com.hostel.management.repository.StudentRepository;
import com.hostel.management.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Student createStudent(Student student, Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + roomId));

        // Check if room has space
        if(room.getOccupiedCount() >= room.getCapacity()){
            throw new RuntimeException("Room " + room.getRoomNumber() + " is already full.");
        }

        student.setRoom(room);

        room.setOccupiedCount(room.getOccupiedCount() + 1);
        roomRepository.save(room);

        return studentRepository.save(student);
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
        student.setAge(updateStudent.getAge());
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
