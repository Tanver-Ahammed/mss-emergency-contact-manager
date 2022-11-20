package com.contact.manager.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "teachers", indexes = {
        @Index(columnList = "contact", name = "contact_index"),
        @Index(columnList = "email", name = "email_index")
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Teacher implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String contact;

    private String email;

    private String bloodGroup;

    private String address;

    private String designation;

    private String maritalStatus;

    private String role;

    private boolean isActive;

    private boolean isEnable;

    private String image;

    private String password;

    @ManyToOne
    @JoinColumn(name = "department_id_fk", referencedColumnName = "id")
    private Department department;

}
