package com.platform.platform.controller;

import com.platform.platform.config.CustomUserDetails;
import com.platform.platform.config.JwtGenerator;
import com.platform.platform.config.StrongPasswordPolicy;
import com.platform.platform.entity.Admin;
import com.platform.platform.entity.Student;
import com.platform.platform.entity.Teacher;
import com.platform.platform.entity.dto.AuthRequest;
import com.platform.platform.entity.dto.AuthResponse;
import com.platform.platform.repo.AdminRepository;
import com.platform.platform.repo.StudentRepository;
import com.platform.platform.repo.TeacherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AdminRepository adminRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtGenerator jwtGenerator;
    private final StrongPasswordPolicy strongPasswordPolicy;

    @PostMapping("/register/student")
    public ResponseEntity<String> registerStudent(@RequestBody Student student) {
        if (studentRepository.existsByEmail(student.getEmail())) {
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        try {
            StrongPasswordPolicy.validatePassword(student.getPassword());
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        student.setPassword(passwordEncoder.encode(student.getPassword()));
        student.setRole("STUDENT");
        studentRepository.save(student);

        return new ResponseEntity<>("Student registered successfully!", HttpStatus.CREATED);
    }

    @PostMapping("/register/teacher")
    public ResponseEntity<String> registerTeacher(@RequestBody Teacher teacher) {
        if (teacherRepository.existsByEmail(teacher.getEmail())) {
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        try {
            StrongPasswordPolicy.validatePassword(teacher.getPassword());
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));
        teacher.setRole("TEACHER");
        teacherRepository.save(teacher);

        return new ResponseEntity<>("Teacher registered successfully!", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        log.info("Login attempt for email: {}", authRequest.getEmail());

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getEmail(),
                            authRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("Authentication successful for: {}", authRequest.getEmail());

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String token = jwtGenerator.generateToken(authentication);
            log.info("Generated token for user: {}", userDetails.getUsername());

            return new ResponseEntity<>(
                    new AuthResponse(token, userDetails.getUserRole(), userDetails.getUserId()),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            log.error("Authentication failed for email: {}", authRequest.getEmail(), e);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}