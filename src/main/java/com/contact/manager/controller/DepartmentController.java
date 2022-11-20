package com.contact.manager.controller;

import com.contact.manager.dto.DepartmentDTO;
import com.contact.manager.dto.TeacherDTO;
import com.contact.manager.services.impl.DepartmentServiceImpl;
import com.contact.manager.services.impl.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping(path = "/department")
public class DepartmentController {

    @Autowired
    private DepartmentServiceImpl departmentService;

    @Autowired
    private TeacherServiceImpl teacherService;

    @GetMapping(path = "/add")
    public String addDepartment(Model model) {
        model.addAttribute("departmentDTO", new DepartmentDTO());
        model.addAttribute("message", "");
        model.addAttribute("dangerMessage", "");
        return "department/add-department";
    }

    @PostMapping(path = "/save")
    public String saveDepartment(@Valid @ModelAttribute("departmentDTO") DepartmentDTO departmentDTO,
                                 Principal principal, Model model,
                                 BindingResult result) {
        if (principal != null) {
            // get logged-in username
            TeacherDTO teacher = this.teacherService.getTeacherDTOIfLoggedIn(principal);
            model.addAttribute("name", teacher.getName());
        } else
            model.addAttribute("name", null);

        model.addAttribute("teacherDTO", new TeacherDTO());
        model.addAttribute("message", "");
        this.departmentService.addDepartment(departmentDTO);
        return "redirect:/department/all";
    }

    @GetMapping(path = "/all")
    public String getAllDepartments(Model model) {
        model.addAttribute("departmentDTOS", this.departmentService.getAllDepartment());
        return "department/all-departments";
    }

    @GetMapping(path = "/get/{departmentId}")
    public String getDepartmentIdById(@PathVariable("departmentId") Long departmentId, Model model) {
        model.addAttribute("departmentDTO", this.departmentService.getDepartmentById(departmentId));
        return "department/single-department";
    }

}
