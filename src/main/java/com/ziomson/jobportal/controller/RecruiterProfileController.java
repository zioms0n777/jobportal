package com.ziomson.jobportal.controller;


import com.ziomson.jobportal.entity.RecruiterProfile;
import com.ziomson.jobportal.entity.Users;
import com.ziomson.jobportal.repository.RecruiterProfileRepository;
import com.ziomson.jobportal.repository.UsersRepository;
import com.ziomson.jobportal.service.RecruiterProfileService;
import com.ziomson.jobportal.utility.FileUploadUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/recruiter-profile")
@AllArgsConstructor
public class RecruiterProfileController {

    private final UsersRepository usersRepository;
    private final RecruiterProfileService recruiterProfileService;

    @GetMapping("/")
public String recruiterProfile(Model model) {
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

       if(!(authentication instanceof AnonymousAuthenticationToken)) {
           String currentUserName = authentication.getName();
           Users user = usersRepository.findByEmail(currentUserName).orElseThrow(()-> new UsernameNotFoundException("Could not find user"));
           Optional<RecruiterProfile> recruiterProfile = recruiterProfileService.getById(user.getUserId());

           if(!(recruiterProfile.isEmpty())) {
               model.addAttribute("profile", recruiterProfile.get());
           }

       }
        return "recruiter_profile";
}

@PostMapping("/addNew")
public String addNew(RecruiterProfile recruiterProfile, @RequestParam("image") MultipartFile image, Model model) {
Authentication authentication=  SecurityContextHolder.getContext().getAuthentication();
    if(!(authentication instanceof AnonymousAuthenticationToken)) {
        String currentUserName = authentication.getName();
        Users user = usersRepository.findByEmail(currentUserName).orElseThrow(()-> new UsernameNotFoundException("Could not find user"));
        recruiterProfile.setUserId(user);
        recruiterProfile.setUserAccountId(user.getUserId());
}
    model.addAttribute("profile", recruiterProfile);
    String fileName = "";
    if (!image.getOriginalFilename().equals("")) {
        fileName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
        recruiterProfile.setProfilePhoto(fileName);
    }
    RecruiterProfile savedUser = recruiterProfileService.addNew(recruiterProfile);

    String uploadDir = "photos/recruiter/" + savedUser.getUserAccountId();
    try {
        FileUploadUtil.saveFile(uploadDir, fileName, image);
    } catch (Exception ex) {
        ex.printStackTrace();
    }

    return "redirect:/dashboard/";
    }
}
