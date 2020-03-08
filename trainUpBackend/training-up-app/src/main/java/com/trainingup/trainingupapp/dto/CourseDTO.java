package com.trainingup.trainingupapp.dto;

import com.trainingup.trainingupapp.enums.CourseType;
import com.trainingup.trainingupapp.enums.Domains;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class CourseDTO {
    private long id;
    private String courseName;
    private int capacity;
    private int actualCapacity;
    private LocalDate startDate;
    private LocalDate endDate;
    private String timeInterval;
    private String projectManager;
    private Domains domain;
    private CourseType type;
}
