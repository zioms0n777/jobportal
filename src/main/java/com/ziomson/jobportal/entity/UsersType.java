package com.ziomson.jobportal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="users_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userTypeId;


    @Column(unique=true)
    private String userTypeName;


    @OneToMany(targetEntity = Users.class, mappedBy = "userTypeId", cascade = CascadeType.ALL)
    private List<Users> users;
}
