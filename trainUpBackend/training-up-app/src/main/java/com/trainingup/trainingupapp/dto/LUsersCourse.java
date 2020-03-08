package com.trainingup.trainingupapp.dto;

import java.util.List;

public class LUsersCourse {
    List<UserDTO> users;
    CourseDTO course;

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }

    public CourseDTO getCourse() {
        return course;
    }

    public void setCourse(CourseDTO course) {
        this.course = course;
    }
}
