package com.example.demo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
@RestController
@RequestMapping("/api")
public class CourseController {

    @GetMapping("/courses")
    public List<Map<String, Object>> getCourses() {
        List<Map<String, Object>> courses = new ArrayList<>();
        courses.add(course("CSIS3275", "Software Engineering", 3));
        courses.add(course("CSIS2200", "Database Systems", 3));
        courses.add(course("CSIS1100", "Introduction to Programming", 3));
        return courses;
    }

    private Map<String, Object> course(String code, String name, int credits) {
        Map<String, Object> c = new LinkedHashMap<>();
        c.put("code", code);
        c.put("name", name);
        c.put("credits", credits);
        return c;
    }
}