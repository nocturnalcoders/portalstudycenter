package com.StudyCenterPortal.controller;

import com.StudyCenterPortal.dto.UserDto;
import com.StudyCenterPortal.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentsController {
    private final UserService service;
    private final ModelMapper modelMapper;

    @Autowired
    private ObjectMapper mapper;

    @ModelAttribute("users")
    public List<UserDto> usersAttribute() {
        return service.findAll()
                .stream()
                .map(entity -> modelMapper.map(entity, UserDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/dashboard")
    @PreAuthorize("@securityService.hasPrivilege('READ_USERS')")
    public String getAdminView(Model model) {
        model.addAttribute("pageTitle", "Dashboard");
        model.addAttribute("pageSubtitle", "welcome");
        model.addAttribute("firstName", service.getCurrentUser().getFirstName());
        return "dashboard";
    }



}
