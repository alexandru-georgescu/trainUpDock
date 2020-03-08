package com.trainingup.trainingupapp.convertor;

import com.trainingup.trainingupapp.dto.CourseDTO;
import com.trainingup.trainingupapp.tables.Course;

public class CourseConvertor {

    public static Course convertToCourse(CourseDTO course) {
        Course newCourse = new Course();
        newCourse.setProjectManager(course.getProjectManager());
        newCourse.setEndDate(course.getEndDate());
        newCourse.setStartDate(course.getStartDate());
        newCourse.setId(course.getId());
        newCourse.setCapacity(course.getCapacity());
        newCourse.setActualCapacity(course.getActualCapacity());
        newCourse.setCourseName(course.getCourseName());
        newCourse.setDomain(course.getDomain());
        newCourse.setType(course.getType());
        newCourse.setTimeInterval(course.getTimeInterval());
        return newCourse;
    }

    public static CourseDTO convertToCourseDTO(Course course) {
        CourseDTO newCourse = new CourseDTO();
        newCourse.setProjectManager(course.getProjectManager());
        newCourse.setEndDate(course.getEndDate());
        newCourse.setStartDate(course.getStartDate());
        newCourse.setId(course.getId());
        newCourse.setCapacity(course.getCapacity());
        newCourse.setActualCapacity(course.getActualCapacity());
        newCourse.setCourseName(course.getCourseName());
        newCourse.setDomain(course.getDomain());
        newCourse.setType(course.getType());
        newCourse.setTimeInterval(course.getTimeInterval());
        return newCourse;
    }
}
