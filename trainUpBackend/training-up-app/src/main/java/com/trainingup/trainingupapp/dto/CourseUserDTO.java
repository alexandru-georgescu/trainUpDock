package com.trainingup.trainingupapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class CourseUserDTO {
    private UserDTO user;
    private CourseDTO course;
}
