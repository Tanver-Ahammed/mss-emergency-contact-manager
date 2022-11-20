package com.contact.manager.services.impl;

import com.contact.manager.entities.Teacher;
import com.contact.manager.model.TeacherUserDetails;
import com.contact.manager.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Author: Md. Tanver Ahammed,
 * ICT, MBSTU
 */

@Service
public class TeacherUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Teacher teacher = this.teacherRepository.findByEmail(username);
        if (teacher == null)
            throw new UsernameNotFoundException("Alumni details not found for this user: " + username);
        return new TeacherUserDetails(teacher);
    }
}
