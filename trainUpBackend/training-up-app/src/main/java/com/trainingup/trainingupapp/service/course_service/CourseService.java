package com.trainingup.trainingupapp.service.course_service;

import com.trainingup.trainingupapp.dto.CourseDTO;
import com.trainingup.trainingupapp.dto.UserDTO;
import com.trainingup.trainingupapp.tables.Course;

import java.util.List;

public interface CourseService {
    List<CourseDTO> findAll();
    List<Course> findAllDB();
    CourseDTO findById(long id);
    Course findByIdDB(long id);
    CourseDTO findByNameDTO(String course);
    CourseDTO addCourse(CourseDTO course);
    void removeCourse(long id);
    List<CourseDTO> findCurrent(UserDTO userDTO);
    List<CourseDTO> findBefore(UserDTO userDTO);
    List<CourseDTO> findFuture(UserDTO user);
    List<CourseDTO> findByPm(UserDTO pm);
    void saveAndFlash(Course course);
    void saveAndFlashBack(CourseDTO courseDTO);
    List<UserDTO> findAcceptedByPm(CourseDTO course);
    List<UserDTO> findRejectedByPm(CourseDTO course);
    boolean updateCourses(List<CourseDTO> courses);
}
