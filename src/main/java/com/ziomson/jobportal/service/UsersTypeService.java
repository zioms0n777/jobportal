package com.ziomson.jobportal.service;

import com.ziomson.jobportal.entity.UsersType;
import com.ziomson.jobportal.repository.UsersTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UsersTypeService {

    private final UsersTypeRepository usersTypeRepository;


public List<UsersType> findAll() {
    return usersTypeRepository.findAll();
}
}
