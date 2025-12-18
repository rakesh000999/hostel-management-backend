package com.hostel.management.service;


import com.hostel.management.entity.Attendance;
import com.hostel.management.entity.Student;

import java.util.List;

public interface AttendanceService {
    Attendance markAttendance(Long studentId, Attendance attendance);
    List<Attendance> getAttendanceByStudent(Long studentId);
    List<Attendance> getAllAttendance();
}
