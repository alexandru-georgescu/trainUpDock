package com.trainingup.trainingupapp.convertor;

import com.trainingup.trainingupapp.dto.CourseDTO;
import com.trainingup.trainingupapp.dto.UserDTO;
import com.trainingup.trainingupapp.tables.Course;
import com.trainingup.trainingupapp.tables.User;

import java.util.ArrayList;
import java.util.List;

public class UserConvertor {
    public static User convertToUser(UserDTO user) {
        User convertedUser = new User();
        convertedUser.setType(user.getType());
        convertedUser.setPassword(user.getPassword());
        convertedUser.setLastName(user.getLastName());
        convertedUser.setFirstName(user.getFirstName());
        convertedUser.setEmail(user.getEmail());
        convertedUser.setId(user.getId());
        convertedUser.setLeader(user.getLeader());
        List<Course> array = new ArrayList<>();
        user.getWishToEnroll().forEach(e -> array.add(CourseConvertor.convertToCourse(e)));
        convertedUser.setWishToEnroll(array);
        convertedUser.setEnable(user.isEnable());
        return convertedUser;
    }

    public static UserDTO convertToUserDTO(User user) {
        UserDTO convertedUser = new UserDTO();
        convertedUser.setType(user.getType());
        convertedUser.setPassword(user.getPassword());
        convertedUser.setLastName(user.getLastName());
        convertedUser.setFirstName(user.getFirstName());
        convertedUser.setEmail(user.getEmail());
        convertedUser.setId(user.getId());
        convertedUser.setLeader(user.getLeader());
        List<CourseDTO> array = new ArrayList<>();
        convertedUser.setEnable(user.isEnable());
        user.getWishToEnroll().forEach(e -> array.add(CourseConvertor.convertToCourseDTO(e)));
        convertedUser.setWishToEnroll(array);
        return convertedUser;
    }
}
