package com.hostel.management.service.impl;

import com.hostel.management.entity.Fee;
import com.hostel.management.entity.Student;
import com.hostel.management.repository.FeeRepository;
import com.hostel.management.repository.StudentRepository;
import com.hostel.management.service.FeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FeeServiceImpl implements FeeService {

    @Autowired
    public FeeRepository feeRepository;

    @Autowired
    public StudentRepository studentRepository;

    @Override
    public Fee createFee(Fee fee) {

        Student student = studentRepository.findById(fee.getStudent().getId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        if(fee.getAmountPaid() >= fee.getAmountDue()){
            fee.setStatus("PAID");
        }else{
            fee.setStatus("DUE");
        }

        fee.setPaymentDate(LocalDate.now());
        fee.setStudent(student);

        return feeRepository.save(fee);
    }

    @Override
    public List<Fee> getAllFees() {
        return feeRepository.findAll();
    }

    @Override
    public Fee getFeeById(Long id) {
        return feeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fee record not found"));
    }

    @Override
    public List<Fee> getFeesByStudent(Long studentId) {
        return feeRepository.findByStudentId(studentId);
    }

    @Override
    public Fee updateFee(Long id, Fee updatedFee) {

        Fee existingFee = getFeeById(id);

        existingFee.setAmountDue(updatedFee.getAmountDue());
        existingFee.setAmountPaid(updatedFee.getAmountPaid());
        existingFee.setMonth(updatedFee.getMonth());
        existingFee.setStudent(updatedFee.getStudent());
        existingFee.setPaymentDate(updatedFee.getPaymentDate());

        return feeRepository.save(existingFee);
    }

    @Override
    public void deleteFee(Long id) {
        feeRepository.deleteById(id);
    }
}
