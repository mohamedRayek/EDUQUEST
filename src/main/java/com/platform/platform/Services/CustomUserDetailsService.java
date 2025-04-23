package com.platform.platform.Services;

import com.platform.platform.config.CustomUserDetails;
import com.platform.platform.entity.Admin;
import com.platform.platform.entity.Student;
import com.platform.platform.entity.Teacher;
import com.platform.platform.entity.User;
import com.platform.platform.repo.AdminRepository;
import com.platform.platform.repo.StudentRepository;
import com.platform.platform.repo.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = adminRepository.findByEmail(email);

        if (user == null) {
            user = teacherRepository.findByEmail(email);
        }

        if (user == null) {
            user = studentRepository.findByEmail(email);
        }

        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        return new CustomUserDetails(user);
    }
}
