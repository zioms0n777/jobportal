package com.ziomson.jobportal.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "recruiter_profile")
@AllArgsConstructor
@Data
@NoArgsConstructor
public class RecruiterProfile {

    @Id

    private int userAccountId;

    @OneToOne
    @JoinColumn(name = "user_account_id")
    @MapsId
    private Users userId;

    private String firstName;
    private String lastName;
    private String city;
    private String state;
    private String country;
    private String company;
    @Column(nullable = true, length = 64)
    private String profilePhoto;

    @Transient
    public String getPhotosImagePath()
    {
        if(profilePhoto == null) return null;
        return "/photo/recruiter/" + userAccountId + "/" + profilePhoto;
    }

    public RecruiterProfile(Users user) {
        this.userId = user;
    }
}
