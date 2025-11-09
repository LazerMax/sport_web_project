package org.example.controllers;

import org.example.dto.UserInfoDto;
import org.example.services.CurrentUserService;
import org.example.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProfileController {

private final CurrentUserService currentUserService;
private final UsersService usersService;

@Autowired
public ProfileController(CurrentUserService currentUserService, UsersService usersService) {
    this.currentUserService = currentUserService;
    this.usersService = usersService;
}

@GetMapping("/profile")
public String getUserInfo(Model model) {
    UserDetails userDetails = currentUserService.getCurrentUserDetails();

    if (userDetails != null) {
        String userName = userDetails.getUsername();
        String profile = usersService.findByUsername(userName).get().getProfile();
        String fullName = usersService.findByUsername(userName).get().getFullName();

        UserInfoDto userInfo = new UserInfoDto();
        userInfo.setUsername(userName);
        userInfo.setProfile(profile);
        userInfo.setFullName(fullName);

        model.addAttribute("user", userInfo);
        return "profile";
    }
    return "redirect:/login";
}

    @GetMapping("/home")
    public String returnHomePage() {
        UserDetails userDetails = currentUserService.getCurrentUserDetails();

        if (userDetails != null) {
            String userName = userDetails.getUsername();
            String profile = usersService.findByUsername(userName).get().getProfile();

            if(profile.equals("Тренер")){
                return "redirect:/trainer";
            } else{
                return "redirect:/student";
            }
        }
        return "redirect:/login";
    }


}