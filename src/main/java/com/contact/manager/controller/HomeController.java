package com.contact.manager.controller;

import com.contact.manager.dto.TeacherDTO;
import com.contact.manager.services.impl.FileServiceImpl;
import com.contact.manager.services.impl.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;

@Controller
public class HomeController {

    @Autowired
    private TeacherServiceImpl teacherService;

    @Autowired
    private FileServiceImpl fileService;

    @Value("${project.image}")
    private String path;

    @RequestMapping(path = {"/", "home"})
    public String home(Model model, Principal principal) {
        if (principal != null) {
            // get logged-in username
            TeacherDTO teacher = this.teacherService.getTeacherDTOIfLoggedIn(principal);
            model.addAttribute("name", teacher.getName());
            return "home";
        }
        model.addAttribute("name", null);
        return "home";
    }

    // login authority
    @GetMapping(path = "/auth/login")
    public String authorityLogin(Model model) {
        model.addAttribute("message", "");
        return "login";
    }

    @GetMapping(path = "/developer")
    public String developer(Principal principal, Model model) {
        if (principal != null) {
            // get logged-in username
            TeacherDTO teacher = this.teacherService.getTeacherDTOIfLoggedIn(principal);
            model.addAttribute("name", teacher.getName());
            return "developer";
        }
        model.addAttribute("name", null);
        return "developer";
    }

    // get image
    @GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response) throws IOException {

        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }

}
