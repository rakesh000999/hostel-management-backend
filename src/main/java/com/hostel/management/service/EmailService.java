package com.hostel.management.service;

import com.hostel.management.entity.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.awt.print.Book;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendBookingEmail(Booking booking){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("rakeshjoshi6078@gmail.com"); // admin email
        message.setSubject("New Booking Received");
        message.setText(
                "A new booking has been made:\n\n" +
                        "Name: " + booking.getFullName() + "\n" +
                        "Email: " + booking.getEmail() + "\n" +
                        "Phone: " + booking.getPhoneNumber() + "\n" +
                        "Check-in: " + booking.getCheckInDate() + "\n" +
                        "Check-out: " + booking.getCheckOutDate() + "\n" +
                        "Status: " + booking.getStatus()
        );

        javaMailSender.send(message);

    }
}
