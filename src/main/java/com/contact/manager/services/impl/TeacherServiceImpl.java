package com.contact.manager.services.impl;

import com.contact.manager.dto.TeacherDTO;
import com.contact.manager.entities.Department;
import com.contact.manager.entities.Teacher;
import com.contact.manager.exceptions.ResourceNotFoundException;
import com.contact.manager.repositories.TeacherRepository;
import com.contact.manager.services.TeacherService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private DepartmentServiceImpl departmentService;

    @Autowired
    private FileServiceImpl fileService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${project.image}")
    private String path;

    @Override
    public TeacherDTO registrationTeacher(TeacherDTO teacherDTO, MultipartFile teacherImage, Long departmentId)
            throws IOException {
        if (teacherImage != null) {
            String alumniImageName = this.fileService.uploadImage(path, teacherImage);
            teacherDTO.setImage(alumniImageName);
        }
        Department department = this.departmentService.getDepartmentById(departmentId);
        Teacher teacher = this.dtoToTeacher(teacherDTO);
        teacher.setDepartment(department);
        teacher.setActive(true);
        teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));
        teacher.setEnable(false);
        return this.teacherToDTO(this.teacherRepository.save(teacher));
    }

    @Override
    public TeacherDTO getSingleTeacherById(Long teacherId) {
        return this.teacherToDTO(this.getTeacherById(teacherId));
    }

    @Override
    public List<TeacherDTO> getAllTeachers() {
        return this.teacherRepository
                .findAll()
                .stream()
                .map(this::teacherToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TeacherDTO> getAllTeacherByDepartment(Long departmentId) {
        Department department = this.departmentService.getDepartmentById(departmentId);
        return this.teacherRepository
                .findTeacherByDepartment(department)
                .stream()
                .map(this::teacherToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TeacherDTO updateTeacher(Long teacherId, TeacherDTO teacherDTO) {
        Teacher teacher = this.getTeacherById(teacherId);
        teacher.setName(teacherDTO.getName());
        teacher.setContact(teacherDTO.getContact());
        teacher.setEmail(teacherDTO.getEmail());
        teacher.setBloodGroup(teacherDTO.getBloodGroup());
        teacher.setAddress(teacherDTO.getAddress());
        teacher.setDesignation(teacherDTO.getDesignation());
        teacher.setMaritalStatus(teacherDTO.getMaritalStatus());
        return this.teacherToDTO(teacher);
    }

    @Override
    public boolean deleteTeacher(Long teacherId) {
        Teacher teacher = this.getTeacherById(teacherId);
        this.teacherRepository.delete(teacher);
        return true;
    }

    @Override
    public Boolean isDuplicateTeacherByEmailOrContact(String email, String contact) {
        Teacher teacher = this.teacherRepository.findByEmailOrContact(email, contact);
        return teacher != null;
    }

    // get teacher by id
    public Teacher getTeacherById(Long teacherId) {
        return this.teacherRepository.findById(teacherId).orElseThrow(() ->
                new ResourceNotFoundException("Teacher", "id", teacherId));
    }


    // get teacher by email
    public Teacher getTeacherByEmail(String email) {
        return this.teacherRepository.findByEmail(email);
    }

    // teacher to DTO
    public TeacherDTO teacherToDTO(Teacher teacher) {
        return this.modelMapper.map(teacher, TeacherDTO.class);
    }

    // dto to teacher
    public Teacher dtoToTeacher(TeacherDTO teacherDTO) {
        return this.modelMapper.map(teacherDTO, Teacher.class);
    }


    public TeacherDTO getTeacherDTOIfLoggedIn(Principal principal) {
        String username = principal.getName();
        TeacherDTO teacherDTO = this.teacherToDTO(this.getTeacherByEmail(username));
        if (teacherDTO.getName().length() > 10)
            teacherDTO.setName(teacherDTO.getName().substring(0, 10));
        teacherDTO.setPassword(null);
        return teacherDTO;
    }

}
