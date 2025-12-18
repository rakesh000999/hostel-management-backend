package com.hostel.management.service.impl;

import com.hostel.management.entity.Room;
import com.hostel.management.entity.Student;
import com.hostel.management.repository.RoomRepository;
import com.hostel.management.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Room addRoom(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Room getRoomById(Long id) {
        Optional<Room> room = roomRepository.findById(id);
        return room.orElse(null);
    }

    @Override
    public Room updateRoom(Long id, Room updatedRoom) {
        Room existingRoom = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found!"));
        existingRoom.setRoomNumber(updatedRoom.getRoomNumber());
        existingRoom.setCapacity(updatedRoom.getCapacity());
        existingRoom.setOccupiedCount(updatedRoom.getOccupiedCount());
        existingRoom.setRentPerMonth(updatedRoom.getRentPerMonth());
//        existingRoom.setStatus(updatedRoom.getStatus());
        return roomRepository.save(existingRoom);
    }

    @Override
    public void deleteRoom(Long id) {
        Room room = roomRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Room not found!"));

        if(room.getOccupiedCount() > 0){
            throw new RuntimeException("Cannot delete room while students are assigned to it.");
        }

        roomRepository.deleteById(id);
    }
}
