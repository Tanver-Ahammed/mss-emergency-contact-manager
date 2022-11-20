package com.contact.manager.services;

import com.contact.manager.dto.TeacherDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TeacherService {

    // add teacher
    TeacherDTO registrationTeacher(TeacherDTO teacherDTO, MultipartFile teacherImage, Long departmentId) throws IOException;

    // get teacher by id
    TeacherDTO getSingleTeacherById(Long teacherId);

    // get all teacher
    List<TeacherDTO> getAllTeachers();

    // get all teacher by department
    List<TeacherDTO> getAllTeacherByDepartment(Long departmentId);

    TeacherDTO updateTeacher(Long teacherId, TeacherDTO teacherDTO);
    // update teacher

    // delete teacher
    boolean deleteTeacher(Long teacherId);

    // duplicate checking
    Boolean isDuplicateTeacherByEmailOrContact(String email, String contact);

}
