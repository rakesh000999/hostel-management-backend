package com.hostel.management.service;

import com.hostel.management.entity.Booking;
import com.hostel.management.entity.BookingStatus;

import java.util.List;

public interface BookingService {
    Booking createBooking(Booking booking);
    Booking getBookingById(Long id);
    List<Booking> getAllBookings();
    Booking updateBooking(Long id, Booking booking);
    void deleteBooking(Long id);
}
