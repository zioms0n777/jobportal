package com.ziomson.jobportal.service;

import com.ziomson.jobportal.entity.JobSeekerProfile;
import com.ziomson.jobportal.entity.RecruiterProfile;
import com.ziomson.jobportal.entity.Users;
import com.ziomson.jobportal.repository.JobSeekerProfileRepository;
import com.ziomson.jobportal.repository.RecruiterProfileRepository;
import com.ziomson.jobportal.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor

public class UsersService {

    private final UsersRepository usersRepository;
private final JobSeekerProfileRepository jobSeekerProfileRepository;
private final RecruiterProfileRepository recruiterProfileRepository;
private final PasswordEncoder passwordEncoder;
    public Users addNew(Users user) {
        user.setActive(true);
        user.setRegistrationDate(new Date(System.currentTimeMillis()));
        user.setPassword(passwordEncoder.encode(user.getPassword()) );
        Users savedUser = usersRepository.save(user);
        int userTypeId = user.getUserTypeId().getUserTypeId();
        if(userTypeId==1)
        {
            recruiterProfileRepository.save(new RecruiterProfile(savedUser));
        }
        else
        {
            jobSeekerProfileRepository.save(new JobSeekerProfile(savedUser));
        }


        return usersRepository.save(user);
    }

    public Optional<Users> getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    public Object getCurrentUserProfile() {
       Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();

        if(!(authentication instanceof AnonymousAuthenticationToken))
        {
            String username = authentication.getName();
          Users user =  usersRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("Could not found user" + username));
          int userId = user.getUserId();
         if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))) {
             RecruiterProfile recruiterProfile = recruiterProfileRepository.findById(userId).orElse(new RecruiterProfile());
             return recruiterProfile;
             }
         else
         {
JobSeekerProfile jobSeekerProfile = jobSeekerProfileRepository.findById(userId).orElse(new JobSeekerProfile());
return jobSeekerProfile;
         }
        }
        return null;
    }
}
