package com.contact.manager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DepartmentDTO implements Serializable {

    private long id;

    private String name;

    private short startingYear;

    private String description;

    private List<TeacherDTO> teacherDTOS = new ArrayList<>();

}
