package com.ziomson.jobportal.service;


import com.ziomson.jobportal.entity.RecruiterProfile;
import com.ziomson.jobportal.repository.RecruiterProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RecruiterProfileService {
private final RecruiterProfileRepository recruiterProfileRepository;

public Optional<RecruiterProfile> getById(Integer id) {
    return recruiterProfileRepository.findById(id);
}

    public RecruiterProfile addNew(RecruiterProfile recruiterProfile) {
    return recruiterProfileRepository.save(recruiterProfile);
    }
}
