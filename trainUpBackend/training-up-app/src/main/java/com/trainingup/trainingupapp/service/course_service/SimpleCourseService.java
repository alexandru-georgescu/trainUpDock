package com.trainingup.trainingupapp.service.course_service;

import com.trainingup.trainingupapp.convertor.CourseConvertor;
import com.trainingup.trainingupapp.dto.CourseDTO;
import com.trainingup.trainingupapp.dto.UserDTO;
import com.trainingup.trainingupapp.enums.CourseType;
import com.trainingup.trainingupapp.repository.CourseRepository;
import com.trainingup.trainingupapp.service.user_service.UserService;
import com.trainingup.trainingupapp.tables.Course;
import com.trainingup.trainingupapp.tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SimpleCourseService implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UserService userService;


    List<CourseDTO> backendCourses = new ArrayList<>();

    public void setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<CourseDTO> findAll() {
        return this.backendCourses;
    }

    @Override
    public List<Course> findAllDB() {
        return courseRepository.findAll();
    }

    @Override
    public CourseDTO findById(long id) {
        return this.backendCourses
                .stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Course findByIdDB(long id) {
        return courseRepository
                .findAll()
                .stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public CourseDTO findByNameDTO(String course) {
        return backendCourses
                .stream()
                .filter(c -> c.getCourseName().toLowerCase().equals(course.toLowerCase()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public CourseDTO addCourse(CourseDTO course) {
        String email = course.getProjectManager().toLowerCase();
        Course newCourse = CourseConvertor.convertToCourse(course);
        this.courseRepository.saveAndFlush(newCourse);
        course.setId(newCourse.getId());
        this.backendCourses.add(course);
        return course;
    }

    @Override
    public void removeCourse(long id) {
        List<UserDTO> usersDTOS = userService.findAll();
        List<User> users = userService.findAllDB();

        //SCOATE CURSUL DE LA FIECARE USER INAINTE DE REMOVE
        usersDTOS.forEach(u -> {
            List<CourseDTO> course1 = u.getCourses();
            course1.removeIf(c -> c.getId() == id);

            List<CourseDTO> course2 = u.getWishToEnroll();
            course2.removeIf(c -> c.getId() == id);

            List<CourseDTO> course3 = u.getWaitToEnroll();
            course3.removeIf(c -> c.getId() == id);

            List<CourseDTO> course4 = u.getRejectedList();
            course4.removeIf(c -> c.getId() == id);

            userService.saveAndFlushBack(u);
        });


        //REZOLVA PROBREMA DE ACCESARE CONCURENTA
        users.forEach(u -> {
            List<Course> d1 = new ArrayList<>();
            List<Course> d2 = new ArrayList<>();
            List<Course> d3 = new ArrayList<>();
            List<Course> d4 = new ArrayList<>();

            List<Course> course1 = u.getCourses();
            course1.removeIf(c -> c.getId() == id);
            d1.addAll(course1);

            List<Course> course2 = u.getWishToEnroll();
            course2.removeIf(c -> c.getId() == id);
            d2.addAll(course2);

            List<Course> course3 = u.getWaitToEnroll();
            course3.removeIf(c -> c.getId() == id);
            d3.addAll(course3);


            List<Course> course4 = u.getRejectedList();
            course4.removeIf(c -> c.getId() == id);
            d4.addAll(course4);

            u.setCourses(d1);
            u.setWishToEnroll(d2);
            u.setWaitToEnroll(d3);
            u.setRejectedList(d4);
            userService.saveAndFlush(u);
        });

        this.courseRepository.deleteById(id);
        backendCourses.removeIf(c -> c.getId() == id);
    }

    @Override
    public List<CourseDTO> findCurrent(UserDTO userDTO) {
        LocalDate now = LocalDate.now();

        return userDTO.getCourses().stream()
                .filter(courseDTO -> courseDTO.getEndDate().isAfter(now))
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDTO> findBefore(UserDTO userDTO) {
        LocalDate now = LocalDate.now();

        return userDTO.getCourses().stream()
                .filter(courseDTO -> courseDTO.getEndDate().isBefore(now))
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDTO> findFuture(UserDTO userDTO) {
        LocalDate now = LocalDate.now();

        List<CourseDTO> all = backendCourses.stream()
                .filter(courseDTO -> courseDTO.getStartDate().isAfter(now))
                .collect(Collectors.toList());

        all.removeIf(c -> userDTO.getWishToEnroll()
                .stream()
                .filter( w -> w.getId() == c.getId()).findFirst()
                .orElse(null) != null);

        all.removeIf(c -> userDTO.getWaitToEnroll()
                .stream()
                .filter( w -> w.getId() == c.getId()).findFirst()
                .orElse(null) != null);

        all.removeIf(c -> userDTO.getRejectedList()
                .stream()
                .filter( w -> w.getId() == c.getId()).findFirst()
                .orElse(null) != null);

        all.removeIf(c -> userDTO.getCourses()
                .stream()
                .filter( w -> w.getId() == c.getId()).findFirst()
                .orElse(null) != null);

        return all;
    }

    @Override
    public List<CourseDTO> findByPm(UserDTO pm) {
        return backendCourses
                .stream()
                .filter(c -> c.getProjectManager().toLowerCase().equals(pm.getEmail().toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public void saveAndFlash(Course course) {
        courseRepository.save(course);
    }

    @Override
    public void saveAndFlashBack(CourseDTO courseDTO) {
        backendCourses = backendCourses.stream().map(c -> {
            if (c.getId() == courseDTO.getId()) {
                c = courseDTO;
            }
            return c;
        }).collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findAcceptedByPm(CourseDTO course) {
        CourseDTO backendDTO = findById(course.getId());

        return userService
                .findAll()
                .stream()
                .filter(u -> {
                    List<CourseDTO> courseDTOS = u.getCourses();
                    CourseDTO dummy = courseDTOS.stream().filter(c -> c.getId() == backendDTO.getId())
                            .findFirst()
                            .orElse(null);
                    if (dummy != null) {
                        return true;
                    }

                    return false;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findRejectedByPm(CourseDTO course) {
        CourseDTO backendDTO = findById(course.getId());

        return userService
                .findAll()
                .stream()
                .filter(u -> {
                    List<CourseDTO> courseDTOS = u.getRejectedList();
                    CourseDTO dummy = courseDTOS.stream().filter(c -> c.getId() == backendDTO.getId())
                            .findFirst()
                            .orElse(null);
                    if (dummy != null) {
                        return true;
                    }

                    return false;

                })
                .collect(Collectors.toList());
    }

    @Override
    public boolean updateCourses(List<CourseDTO> courses) {
        courses.forEach(c -> {
            Course db = findByIdDB(c.getId());
            CourseDTO dto = findById(c.getId());

            db.setCourseName(c.getCourseName());
            db.setProjectManager(c.getProjectManager());
            db.setDomain(c.getDomain());

            dto.setCourseName(c.getCourseName());
            dto.setProjectManager(c.getProjectManager());
            dto.setDomain(c.getDomain());

            saveAndFlash(db);
            saveAndFlashBack(dto);
        });

        return true;
    }
}
