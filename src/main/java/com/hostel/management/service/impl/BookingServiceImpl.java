package com.hostel.management.service.impl;

import com.hostel.management.entity.Booking;
import com.hostel.management.repository.BookingRepository;
import com.hostel.management.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Override
    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id "+id));
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking updateBooking(Long id, Booking booking) {
        Booking newBooking = getBookingById(id);

        newBooking.setFullName(booking.getFullName());
        newBooking.setEmail(booking.getEmail());
        newBooking.setPhoneNumber(booking.getPhoneNumber());
        newBooking.setCheckInDate(booking.getCheckInDate());
        newBooking.setCheckOutDate(booking.getCheckOutDate());
        newBooking.setStatus(booking.getStatus());

        return bookingRepository.save(newBooking);
    }

    @Override
    public void deleteBooking(Long id) {

    }
}
