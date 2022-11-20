package com.contact.manager.controller;

import com.contact.manager.dto.TeacherDTO;
import com.contact.manager.services.impl.DepartmentServiceImpl;
import com.contact.manager.services.impl.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping(path = "/teacher")
public class TeacherController {

    @Autowired
    private TeacherServiceImpl teacherService;

    @Autowired
    private DepartmentServiceImpl departmentService;

    @GetMapping(path = "/registration")
    public String registrationTeacher(Model model, Principal principal) {

        if (principal != null) {
            // get logged-in username
            TeacherDTO teacher = this.teacherService.getTeacherDTOIfLoggedIn(principal);
            model.addAttribute("name", teacher.getName());
        } else
            model.addAttribute("name", null);

        model.addAttribute("departmentDTOS", this.departmentService.getAllDepartment());
        model.addAttribute("departmentId", 0);
        model.addAttribute("teacherDTO", new TeacherDTO());
        model.addAttribute("message", "");

        return "teacher/registration-teacher";
    }

    @GetMapping(path = "/registration/{departmentId}")
    public String registrationTeacherByDeptId(@PathVariable("departmentId") Long departmentId,
                                              Model model, Principal principal) {
        if (principal != null) {
            // get logged-in username
            TeacherDTO teacher = this.teacherService.getTeacherDTOIfLoggedIn(principal);
            model.addAttribute("name", teacher.getName());
        } else
            model.addAttribute("name", null);
        model.addAttribute("departmentId", departmentId);
        model.addAttribute("teacherDTO", new TeacherDTO());
        model.addAttribute("message", "");
        return "teacher/registration-teacher";
    }

    @PostMapping(path = "/save")
    public String saveTeacher(@Valid @ModelAttribute("teacherDTO") TeacherDTO teacherDTO, BindingResult result,
                              @RequestParam("departmentId") Long departmentId,
                              @RequestParam("teacherImage") MultipartFile teacherImage,
                              Model model, Principal principal) throws IOException {

        if (principal != null) {
            // get logged-in username
            TeacherDTO teacher = this.teacherService.getTeacherDTOIfLoggedIn(principal);
            model.addAttribute("name", teacher.getName());
            return "teacher/registration-teacher";
        } else
            model.addAttribute("name", null);

        // unique identity email and contact
        Boolean isDuplicateEmailOrContact = this.teacherService
                .isDuplicateTeacherByEmailOrContact(teacherDTO.getEmail(), teacherDTO.getContact());
        if (isDuplicateEmailOrContact) {
            model.addAttribute("dangerMessage", "email or contact already exist!!");
            return "teacher/registration-teacher";
        }

        TeacherDTO resultTeacherDTO = this.teacherService
                .registrationTeacher(teacherDTO, teacherImage, departmentId);

        model.addAttribute("teacherDTO", new TeacherDTO());
        model.addAttribute("message", "teacher is successfully added...");
        return "teacher/registration-teacher";
    }

    @GetMapping(path = "/all")
    public String getAllTeachers(Model model) {
        model.addAttribute("teacherDTOS", this.teacherService.getAllTeachers());
        return "teacher/all-teachers";
    }

    @GetMapping(path = "/all/by/department/{departmentId}")
    public String getAllTeachersByDepartment(@PathVariable("departmentId") Long departmentId, Model model) {
        model.addAttribute("teacherDTOS", this.teacherService.getAllTeacherByDepartment(departmentId));
        return "teacher/all-teachers";
    }

    @GetMapping(path = "/get/{teacherId}")
    public String geTeacherIddById(@PathVariable("teacherId") Long teacherId, Model model) {
        model.addAttribute("teacherDTO", this.teacherService.getTeacherById(teacherId));
        return "teacher/single-teacher";
    }

    // get alumni by id
    @GetMapping(path = "/my/profile")
    public String getAlumniById(Model model, Principal principal) {
        TeacherDTO teacher;
        if (principal != null) {
            // get logged-in username
            teacher = this.teacherService.getTeacherDTOIfLoggedIn(principal);
            model.addAttribute("name", teacher.getName());
        } else {
            model.addAttribute("name", null);
            return "redirect:/auth/login";
        }
        model.addAttribute("teacherDTO", this.teacherService.getSingleTeacherById(teacher.getId()));
        return "teacher/single-teacher";
    }

}
