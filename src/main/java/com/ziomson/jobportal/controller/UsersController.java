package com.ziomson.jobportal.controller;

import com.ziomson.jobportal.entity.Users;
import com.ziomson.jobportal.entity.UsersType;
import com.ziomson.jobportal.service.UsersService;
import com.ziomson.jobportal.service.UsersTypeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class UsersController {

    private final UsersTypeService usersTypeService;
private final UsersService usersService;


@GetMapping("/register")
    public String register(Model model) {
        List<UsersType> usersTypes = usersTypeService.findAll();
        model.addAttribute("usersTypes", usersTypes);
        model.addAttribute("user", new Users());
        return "register";

    }
    @PostMapping("/register/new")
public String userRegistration(@Valid Users user) {
    usersService.addNew(user);
    return "redirect:/dashboard/";
}

    @GetMapping("/login")
    public String login()
    {
        return "login";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }


}
