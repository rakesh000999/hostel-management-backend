package com.hostel.management.service.impl;

import com.hostel.management.entity.Staff;
import com.hostel.management.repository.StaffRepository;
import com.hostel.management.service.StaffService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;

    public StaffServiceImpl(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    public Staff addStaff(Staff staff) {
        return staffRepository.save(staff);
    }

    @Override
    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    @Override
    public Staff getStaffById(Long id) {
        return staffRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Staff not found with id - " + id));
    }

    @Override
    public Staff updateStaff(Long id, Staff staff) {

        Staff existingStaff = getStaffById(id);

        existingStaff.setName(staff.getName());
        existingStaff.setRole(staff.getRole());
        existingStaff.setGender(staff.getGender());
        existingStaff.setPhone(staff.getPhone());
        existingStaff.setSalary(staff.getSalary());
        existingStaff.setAddress(staff.getAddress());
        existingStaff.setDutyShift(staff.getDutyShift());

        return staffRepository.save(existingStaff);
    }

    @Override
    public void deleteStaff(Long id) {
        staffRepository.deleteById(id);
    }
}
