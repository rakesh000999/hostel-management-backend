package com.hostel.management.controller;

import com.hostel.management.entity.Attendance;
import com.hostel.management.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin("*")
public class AttendanceController {

    @Autowired
    public AttendanceService attendanceService;

    @PostMapping("/{studentId}/attendance")
    public Attendance markAttendance(@PathVariable Long studentId, @RequestBody Attendance attendance){
        return attendanceService.markAttendance(studentId, attendance);
    }

    @GetMapping("/{studentId}/attendance")
    public List<Attendance> getAttendanceByStudent(@PathVariable Long studentId){
        return attendanceService.getAttendanceByStudent(studentId);
    }

    @GetMapping
    public List<Attendance> getAllAttendance(){
        return attendanceService.getAllAttendance();
    }

}
