//package com.hostel.management.entity;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.time.LocalDate;
//
//@Entity
//@Data
//@Table(name = "students")
//public class Student {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Long id;
//
//    private String name;
//
//    private int dob;
//
//    private String gender;
//
//    private String nationality;
//
//    @Column(unique = true)
//    private String email;
//
//    private String phone;
//
//    private String address;
//
//    @Column(name = "check_in_date")
//    private LocalDate checkInDate;
//
//    @Column(name = "check_out_date")
//    private LocalDate checkOutDate;
//
//    @Column(name = "guardian_name")
//    private String guardianName;
//
//    @Column(name = "guardian_contact")
//    private String guardianContact;
//
//    @Column(name = "emergency_contact", nullable = false)
//    private String emergencyContact;
//
//    @Column(name = "identity_document_path")
//    private String identityDocumentPath;
//
//    @Column(name = "photo_path")
//    private String photoPath;
//
//    @Lob
//    @Column(name = "photo_aes_key")
//    private byte[] photoAesKey;
//
//    @Lob
//    @Column(name = "identity_aes_key")
//    private byte[] identityAesKey;
//
//    @Column(name = "photo_content_type")
//    private String photoContentType;
//
//    @Column(name = "identity_content_type")
//    private String identityContentType;
//
//    @ManyToOne
//    @JoinColumn(name = "room_id")
//    @JsonBackReference
//    private Room room;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getGender() {
//        return gender;
//    }
//
//    public void setGender(String gender) {
//        this.gender = gender;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public Room getRoom() {
//        return room;
//    }
//
//    public void setRoom(Room room) {
//        this.room = room;
//    }
//
//    public String getGuardianName() {
//        return guardianName;
//    }
//
//    public void setGuardianName(String guardianName) {
//        this.guardianName = guardianName;
//    }
//
//    public String getGuardianContact() {
//        return guardianContact;
//    }
//
//    public void setGuardianContact(String guardianContact) {
//        this.guardianContact = guardianContact;
//    }
//
//    @Override
//    public String toString() {
//        return "Student{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", dob=" + dob +
//                ", gender='" + gender + '\'' +
//                ", email='" + email + '\'' +
//                ", phone='" + phone + '\'' +
//                ", address='" + address + '\'' +
//                ", room=" + room +
//                '}';
//    }
//}
//
//

package com.hostel.management.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    private String name;

    @NotNull(message = "Date of birth is required")
    @Min(value = 1900, message = "Invalid year of birth")
    private int dob;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotBlank(message = "Nationality is required")
    private String nationality;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Phone number must be 10 digits"
    )
    private String phone;

    @NotBlank(message = "Address is required")
    @Size(max = 255, message = "Address is too long")
    private String address;

    @NotNull(message = "Check-in date is required")
    @Column(name = "check_in_date")
    private LocalDate checkInDate;

    @FutureOrPresent(message = "Check-out date must be today or in the future")
    @Column(name = "check_out_date")
    private LocalDate checkOutDate;

    @NotBlank(message = "Guardian name is required")
    private String guardianName;

    @NotBlank(message = "Guardian contact is required")
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Guardian contact must be 10 digits"
    )
    private String guardianContact;

    @NotBlank(message = "Emergency contact is required")
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Emergency contact must be 10 digits"
    )
    @Column(nullable = false)
    private String emergencyContact;

    private String identityDocumentPath;
    private String photoPath;

//    @Size(min = 64, max = 64, message = "Invalid file hash")
//    private String fileHash;

    @Lob
    private byte[] photoAesKey;

    @Lob
    private byte[] identityAesKey;

    private String photoContentType;
    private String identityContentType;

    @ManyToOne
    @JoinColumn(name = "room_id")
    @JsonBackReference
    @NotNull(message = "Room assignment is required")
    private Room room;
}

