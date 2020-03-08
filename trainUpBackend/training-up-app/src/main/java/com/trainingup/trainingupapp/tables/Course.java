package com.trainingup.trainingupapp.tables;

import com.trainingup.trainingupapp.enums.CourseType;
import com.trainingup.trainingupapp.enums.Domains;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import javax.persistence.*;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Accessors(chain = true)
@Entity
@Table(name="COURSES")
@EnableAutoConfiguration
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
