package com.hostel.management.controller;

import com.hostel.management.entity.Fee;
import com.hostel.management.service.FeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fees")
public class FeeController {

    @Autowired
    private FeeService feeService;

    @PostMapping
    public Fee createFee(@RequestBody Fee fee){
        return feeService.createFee(fee);
    }

    @GetMapping
    public List<Fee> getAllFees(){
        return feeService.getAllFees();
    }

    @GetMapping("/{id}")
    public Fee getFeeById(@PathVariable Long id){
        return feeService.getFeeById(id);
    }

    @GetMapping("/student/{studentId}")
    public List<Fee> getFeesByStudent(@PathVariable Long studentId){
        return feeService.getFeesByStudent(studentId);
    }

    @PutMapping("/{id}")
    public Fee updateFee(@PathVariable Long id, @RequestBody Fee fee){
        return feeService.updateFee(id, fee);
    }

    @DeleteMapping("/{id}")
    public void deleteFee(@PathVariable Long id){
        feeService.deleteFee(id);
    }
}
