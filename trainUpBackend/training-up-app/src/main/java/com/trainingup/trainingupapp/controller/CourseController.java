package com.trainingup.trainingupapp.controller;

import com.trainingup.trainingupapp.dto.CourseDTO;
import com.trainingup.trainingupapp.dto.UserDTO;
import com.trainingup.trainingupapp.service.course_service.CourseService;
import com.trainingup.trainingupapp.service.user_service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;


    @GetMapping("/course")
    public List<CourseDTO> introProject() {
        return courseService.findAll();
    }

    @PostMapping("/course/findByPm")
    public List<CourseDTO> findByPm(@RequestBody UserDTO pm) {
        return courseService.findByPm(pm);
    }

    @PostMapping("/course/isCurrent")
    public List<CourseDTO> findCurrent(@RequestBody UserDTO userDTO) {
        return courseService.findCurrent(userDTO);
    }


    @PostMapping("/course/isBefore")
    public List<CourseDTO> findBefore(@RequestBody UserDTO userDTO) {
        return courseService.findBefore(userDTO);
    }

    @PostMapping("/course/isFuture")
    public List<CourseDTO> findFuture(@RequestBody UserDTO user) {
        UserDTO userDTO = userService.findById(user.getId());
        return courseService.findFuture(userDTO);
    }

    @PostMapping("/course/add")
    public CourseDTO addCoursePage(@RequestBody CourseDTO course) {
        return courseService.addCourse(course);
    }

    @GetMapping("/course/remove")
    public void removeCourseByIdPage(@RequestParam("id") long id) {
        courseService.removeCourse(id);
    }

    @GetMapping("/course/find")
    public CourseDTO findCourseByIdPage(@RequestParam("id") long id) {
        return courseService.findById(id);
    }

    @PostMapping("/course/findAcceptedByPm")
    public List<UserDTO> findAcceptedByPm(@RequestBody CourseDTO course) {
        return courseService.findAcceptedByPm(course);
    }

    @PostMapping("/course/findRejectedByPm")
    public List<UserDTO> findRejectedByPm(@RequestBody CourseDTO course) {
        return courseService.findRejectedByPm(course);
    }

    @PostMapping("/course/update_courses")
    public boolean updateCourse(@RequestBody List<CourseDTO> courses) {
        return courseService.updateCourses(courses);
    }
}
