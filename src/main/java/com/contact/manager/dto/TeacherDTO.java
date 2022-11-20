package com.contact.manager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TeacherDTO implements Serializable {

    private Long id;

    private String name;

    private String contact;

    private String email;

    private String bloodGroup;

    private String address;

    private String designation;

    private String maritalStatus;

    private boolean isActive;

    private boolean isEnable;

    private String image;

    private String password;

    private DepartmentDTO departmentDTO;

}
