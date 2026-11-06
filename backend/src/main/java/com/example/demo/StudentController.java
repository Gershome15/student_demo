package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
@RestController
@RequestMapping("/api")
public class StudentController {

    // in-memory "database"
    private static final Map<String, Student> studentDatabase = new ConcurrentHashMap<>();

    // generate a test account
    static {
        Student defaultStudent = new Student();
        defaultStudent.setStudentId("1001");
        defaultStudent.setPassword("123456");
        defaultStudent.setName("Test Student");
        defaultStudent.setEmail("test@school.edu");
        studentDatabase.put("1001", defaultStudent);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String studentId = loginRequest.get("studentId");
        String password = loginRequest.get("password");

        // 1. verify the input is not empty
        if (studentId == null || studentId.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Student ID and Password cannot be empty.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        // 2. check the student existence
        Student student = studentDatabase.get(studentId);

        // 3. validate the login info
        if (student != null && student.getPassword().equals(password)) {
            Map<String, Object> response = new HashMap<>();
            response.put("id", student.getStudentId());
            response.put("name", student.getName());
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Invalid Student ID or Password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerStudent(@RequestBody Student newStudent) {
        // validate data, make sure not null
        if (newStudent.getStudentId() == null || newStudent.getStudentId().trim().isEmpty() ||
            newStudent.getPassword() == null || newStudent.getPassword().trim().isEmpty()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Student ID and Password cannot be empty.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        // check whether the studentId has been created
        if (studentDatabase.containsKey(newStudent.getStudentId())) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Registration failed: Student ID already exists.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }

        // unique ID, "insert" into the simulated database
        studentDatabase.put(newStudent.getStudentId(), newStudent);

        Map<String, String> successResponse = new HashMap<>();
        successResponse.put("message", "Registration successful!");
        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }
}