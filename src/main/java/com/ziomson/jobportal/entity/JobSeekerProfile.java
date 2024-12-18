package com.ziomson.jobportal.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "job_seeker_profile")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobSeekerProfile {

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
private String employmentType;
private String workAuthorization;
@Column(nullable = true,length = 64)
private String profilePhoto;

@OneToMany(targetEntity = Skills.class, cascade = CascadeType.ALL, mappedBy = "jobSeekerProfile")
private List<Skills> skills;


    public JobSeekerProfile(Users user) {
        this.userId=user;
    }
}
