package com.hostel.management.service;

import com.hostel.management.entity.Staff;

import java.util.List;

public interface StaffService {
     Staff addStaff(Staff staff);
     List<Staff> getAllStaff();
     Staff getStaffById(Long id);
     Staff updateStaff(Long id, Staff staff);
     void deleteStaff(Long id);
}
