package com.trainingup.trainingupapp.repository;

import com.trainingup.trainingupapp.tables.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}