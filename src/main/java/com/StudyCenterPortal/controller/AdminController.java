package com.StudyCenterPortal.controller;

import com.StudyCenterPortal.dto.UserDto;
import com.StudyCenterPortal.dto.UserRegistrationDto;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final ModelMapper modelMapper;

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private String MENU = "admins";
    private String FOLDER_NAME_STUDENT = "admins/students/";
    private String FOLDER_NAME_TEACHER = "admins/teachers/";


    private String TITLE_STUDENTS = "Students Management";
    private String TITLE_TEACHERS = "Teachers Management";

    @ModelAttribute("users")
    public List<UserDto> usersAttribute() {
        return userService.findAll()
                .stream()
                .map(entity -> modelMapper.map(entity, UserDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/dashboard")
    @PreAuthorize("@securityService.hasPrivilege('READ_USERS')")
    public String getAdminView(Model model) {
        model.addAttribute("pageTitle", "Dashboard");
        model.addAttribute("pageSubtitle", "welcome");
        model.addAttribute("firstName", userService.getCurrentUser().getFirstName());
        return "dashboard";
    }

    //students
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
        model.addAttribute("pageTitle", TITLE_STUDENTS);
        model.addAttribute("pageSubTitle", "List " + TITLE_STUDENTS);
        model.addAttribute("firstName", userService.getCurrentUser().getFirstName());
        //============================================
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

        if (!model.containsAttribute("holder")) {
            model.addAttribute("holder", new UserRegistrationDto());
        }

        model.addAttribute("activeMenu", MENU);
        model.addAttribute("pageTitle", TITLE_STUDENTS);
        model.addAttribute("pageSubTitle", "List " + TITLE_STUDENTS);
        model.addAttribute("firstName", userService.getCurrentUser().getFirstName());
        return FOLDER_NAME_STUDENT + "create";
    }

    @PostMapping(path = "/student/add")
    @PreAuthorize(("@securityService.hasPrivilege('CREATE_USERS')"))
    public String PostaddStudents(@Valid @ModelAttribute("holder") UserRegistrationDto userDto, BindingResult result) {
        logger.info("/student/add");
        User existing = userService.findByEmail(userDto.getEmail());
        if (existing != null){
            result.rejectValue("email", "There is already an account registered with that email");
        }
        if (result.hasErrors()){
            logger.info("error");
            return "student/add";
        }

        userService.save_student(userDto);

        return FOLDER_NAME_STUDENT + "create";
    }
    //Teacher
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
        model.addAttribute("pageTitle", TITLE_TEACHERS);
        model.addAttribute("pageSubTitle", "List " + TITLE_TEACHERS);
        model.addAttribute("firstName", userService.getCurrentUser().getFirstName());
        //============================================
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
        if (!model.containsAttribute("holder")) {
            model.addAttribute("holder", new UserRegistrationDto());
        }

        model.addAttribute("activeMenu", MENU);
        model.addAttribute("pageTitle", TITLE_TEACHERS);
        model.addAttribute("pageSubTitle", "List " + TITLE_TEACHERS);
        model.addAttribute("firstName", userService.getCurrentUser().getFirstName());
        //==================================================================================

        return FOLDER_NAME_TEACHER + "create";
    }

    @PostMapping(path = "/teacher/add")
    @PreAuthorize(("@securityService.hasPrivilege('CREATE_USERS')"))
    public String PostaddTeachers(@Valid @ModelAttribute("holder") UserRegistrationDto userDto, BindingResult result) {
        logger.info("/teacher/add");
        User existing = userService.findByEmail(userDto.getEmail());
        if (existing != null){
            result.rejectValue("email", "There is already an account registered with that email");
        }
        if (result.hasErrors()){
            logger.info("error");
            return "teacher/add";
        }

        userService.save_teacher(userDto);

        return FOLDER_NAME_TEACHER + "create";
    }

}
