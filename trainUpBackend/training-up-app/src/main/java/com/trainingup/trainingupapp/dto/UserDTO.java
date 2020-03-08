package com.trainingup.trainingupapp.dto;

import com.trainingup.trainingupapp.enums.UserType;
import com.trainingup.trainingupapp.tables.Course;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class UserDTO {
    private long id;
    private String email;
    private UserType type;
    private String firstName;
    private String lastName;
    private String password;
    private boolean enable;
    private String leader;

    private List<CourseDTO> courses = new ArrayList<>();
    private List<CourseDTO> wishToEnroll = new ArrayList<>();
    private List<CourseDTO> waitToEnroll = new ArrayList<>();
    private List<CourseDTO> rejectedList = new ArrayList<>();

}
