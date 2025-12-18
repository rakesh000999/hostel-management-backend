package com.hostel.management.service;

import com.hostel.management.entity.Fee;

import java.util.List;

public interface FeeService {
    Fee createFee(Fee fee);
    List<Fee> getAllFees();
    Fee getFeeById(Long id);
    List<Fee> getFeesByStudent(Long studentId);
    Fee updateFee(Long id, Fee fee);
    void deleteFee(Long id);
}
