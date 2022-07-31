package com.StudyCenterPortal.controller;

import com.StudyCenterPortal.dto.UserDto;
import com.StudyCenterPortal.model.User;
import com.StudyCenterPortal.service.StudentService;
import com.StudyCenterPortal.service.TeacherService;
import com.StudyCenterPortal.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService service;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final ModelMapper modelMapper;

    @Autowired
    private ObjectMapper mapper;

    private static final Logger logger = LoggerFactory.getLogger(StudentsController.class);
    private String MENU = "admins";
    private String FOLDER_NAME_STUDENT = "admins/students/";
    private String FOLDER_NAME_TEACHER = "admins/teachers/";


    private String TITLE = "Students Management";

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

    @GetMapping(path = "/student")
    @PreAuthorize("@securityService.hasPrivilege('READ_USERS')")
    public String mainStudent(Model model) {
        logger.info("/students");
        if (model.asMap().containsKey("formBindingResult")) {
            model.addAttribute("org.springframework.validation.BindingResult.holder",
                    model.asMap().get("formBindingResult"));
        }

        if (!model.containsAttribute("holder")) {
            model.addAttribute("holder", new User());
        }
        model.addAttribute("activeMenu", MENU);
        model.addAttribute("pageTitle", TITLE);
        model.addAttribute("pageSubTitle", "List " + TITLE);
        model.addAttribute("firstName", service.getCurrentUser().getFirstName());
        //============================================
//        model.addAttribute("listStudents", );

        model.addAttribute("listStudents", studentService.findByAllStudentRole());
        return FOLDER_NAME_STUDENT + "index";
    }


    @GetMapping(path = "/student/add")
    @PreAuthorize("@securityService.hasPrivilege('READ_USERS')")
    public String addStudents(Model model) {
        if (model.asMap().containsKey("formBindingResult")) {
            model.addAttribute("org.springframework.validation.BindingResult.holder",
                    model.asMap().get("formBindingResult"));
        }
        logger.info("/students");

        model.addAttribute("activeMenu", MENU);
        model.addAttribute("pageTitle", TITLE);
        model.addAttribute("pageSubTitle", "List " + TITLE);
        model.addAttribute("firstName", service.getCurrentUser().getFirstName());
        return FOLDER_NAME_STUDENT + "create";
    }

    @GetMapping(path = "/teacher")
    @PreAuthorize("@securityService.hasPrivilege('READ_USERS')")
    public String mainTeacher(Model model) {
        logger.info("/students");
        if (model.asMap().containsKey("formBindingResult")) {
            model.addAttribute("org.springframework.validation.BindingResult.holder",
                    model.asMap().get("formBindingResult"));
        }

        if (!model.containsAttribute("holder")) {
            model.addAttribute("holder", new User());
        }
        model.addAttribute("activeMenu", MENU);
        model.addAttribute("pageTitle", TITLE);
        model.addAttribute("pageSubTitle", "List " + TITLE);
        model.addAttribute("firstName", service.getCurrentUser().getFirstName());
        //============================================
//        model.addAttribute("listStudents", );
        model.addAttribute("listTeachers", teacherService.findByAllTeacherRole());
        return FOLDER_NAME_TEACHER + "index";
    }


    @GetMapping(path = "/teacher/add")
    @PreAuthorize("@securityService.hasPrivilege('READ_USERS')")
    public String addTeacher(Model model) {
        if (model.asMap().containsKey("formBindingResult")) {
            model.addAttribute("org.springframework.validation.BindingResult.holder",
                    model.asMap().get("formBindingResult"));
        }
        logger.info("/students");

        model.addAttribute("activeMenu", MENU);
        model.addAttribute("pageTitle", TITLE);
        model.addAttribute("pageSubTitle", "List " + TITLE);
        model.addAttribute("firstName", service.getCurrentUser().getFirstName());
        //==================================================================================

        return FOLDER_NAME_TEACHER + "create";
    }
}
