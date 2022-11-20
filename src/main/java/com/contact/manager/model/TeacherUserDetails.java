package com.contact.manager.model;

import com.contact.manager.entities.Teacher;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TeacherUserDetails implements UserDetails {

    private final Teacher teacher;

    public TeacherUserDetails(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(teacher.getRole()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.teacher.getPassword();
    }

    @Override
    public String getUsername() {
        return this.teacher.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.teacher.isActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.teacher.isEnable();
    }
}
