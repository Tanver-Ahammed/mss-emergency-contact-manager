package com.contact.manager.repositories;

import com.contact.manager.entities.Department;
import com.contact.manager.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    List<Teacher> findTeacherByDepartment(Department department);

    Teacher findByEmail(String email);

    Teacher findByEmailOrContact(String email, String contact);
}
