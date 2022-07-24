package com.StudyCenterPortal.controller;

import com.StudyCenterPortal.dto.UserRegistrationDto;
import com.StudyCenterPortal.model.User;
import com.StudyCenterPortal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/registration_student")
public class UserRegistrationStudentController {

    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        return "registration_student";
    }

    @PostMapping
    public String registerUserAccount_student(@ModelAttribute("user") @Valid UserRegistrationDto userDto,
                                      BindingResult result){

        User existing = userService.findByEmail(userDto.getEmail());
        if (existing != null){
            result.rejectValue("email", "There is already an account registered with that email");
        }

        if (result.hasErrors()){
            return "registration_student";
        }

        userService.save_student(userDto);
        return "redirect:/registration_students?success";
    }


}
