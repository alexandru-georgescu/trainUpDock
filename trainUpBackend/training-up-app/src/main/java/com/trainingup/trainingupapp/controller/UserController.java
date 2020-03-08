
package com.trainingup.trainingupapp.controller;

import com.trainingup.trainingupapp.dto.*;
import com.trainingup.trainingupapp.enums.CourseType;
import com.trainingup.trainingupapp.enums.Domains;
import com.trainingup.trainingupapp.enums.UserType;
import com.trainingup.trainingupapp.repository.EmailRepository;
import com.trainingup.trainingupapp.service.course_service.CourseService;
import com.trainingup.trainingupapp.service.user_service.UserService;
import com.trainingup.trainingupapp.tables.Course;
import com.trainingup.trainingupapp.tables.EmailTemplate;
import com.trainingup.trainingupapp.tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private EmailRepository emailRepository;

    int course = 1;

    @GetMapping("/user")
    public List<UserDTO> introProject() {
        return userService.findAll();
    }

    @GetMapping("/userDB")
    public List<User> introProject1() {
        return userService.findAllDB();
    }



    @PostConstruct
    public void createAdmin() {
        String PM_SOFT = "paul.sava@trainup.com";
        String PM_TECH = "paul.tiberiu@trainup.com";
        String PM_PROC = "paul.popescu@trainup.com";
        String TM = "tudor.mihai@trainup.com";

        EmailTemplate userr = new EmailTemplate();
        userr.setEmail("a.alexandru.georgescu@gmail.com");
        userr.setTrainUpEmail("alex.georgescu@trainup.com");
        userr.setUserType(UserType.USER);


        EmailTemplate userr1 = new EmailTemplate();
        userr1.setEmail("cr1st17115@gmail.com");
        userr1.setTrainUpEmail(TM);
        userr1.setUserType(UserType.TM);


        EmailTemplate userr2 = new EmailTemplate();
        userr2.setEmail("wblueme@gmail.com");
        userr2.setTrainUpEmail(PM_TECH);
        userr2.setUserType(UserType.PMTECH);


        emailRepository.saveAndFlush(userr);
        emailRepository.saveAndFlush(userr1);
        emailRepository.saveAndFlush(userr2);

        UserDTO admin = new UserDTO();
        admin.setType(UserType.ADMIN);
        admin.setLeader("ALEX_G_SEFU");
        admin.setEmail("admin.admin@trainup.com");
        admin.setFirstName("Admin");
        admin.setLastName("Admin");
        admin.setPassword("Admin123456");
        admin.setEnable(true);

        UserDTO pm = new UserDTO();
        pm.setType(UserType.PMTECH);
        pm.setEmail(PM_TECH);
        pm.setFirstName("Paul");
        pm.setLastName("Tiberiu");
        pm.setPassword("Pm123456");
        pm.setLeader("admin.admin@trainup.com");
        pm.setEnable(true);


        UserDTO pm2 = new UserDTO();
        pm2.setType(UserType.PMSOFT);
        pm2.setEmail(PM_SOFT);
        pm2.setFirstName("Paul");
        pm2.setLastName("Sava");
        pm2.setPassword("Pm123456");
        pm2.setLeader("admin.admin@trainup.com");
        pm2.setEnable(true);


        UserDTO pm1 = new UserDTO();
        pm1.setType(UserType.PMPROC);
        pm1.setEmail(PM_PROC);
        pm1.setFirstName("Paul");
        pm1.setLastName("Popescu");
        pm1.setPassword("Pm123456");
        pm1.setLeader("admin.admin@trainup.com");
        pm1.setEnable(true);

        UserDTO tm = new UserDTO();
        tm.setType(UserType.TM);
        tm.setEmail(TM);
        tm.setFirstName("Tudor");
        tm.setLastName("Mihai");
        tm.setPassword("Tm123456");
        tm.setLeader(PM_TECH);
        tm.setEnable(true);

        UserDTO user = new UserDTO();
        user.setType(UserType.USER);
        user.setEmail("udreanu.stoica@trainup.com");
        user.setFirstName("Udreanu");
        user.setLastName("Stoica");
        user.setPassword("User123456");
        user.setLeader(TM);
        user.setEnable(true);

        UserDTO user1 = new UserDTO();
        user1.setType(UserType.USER);
        user1.setEmail("alex.georgescu@trainup.com");
        user1.setFirstName("Alex");
        user1.setLastName("Gerogescu");
        user1.setPassword("User123456");
        user1.setLeader(TM);
        user1.setEnable(true);


        UserDTO user2 = new UserDTO();
        user2.setType(UserType.USER);
        user2.setEmail("eda.ibram@trainup.com");
        user2.setFirstName("Eda");
        user2.setLastName("Ibram");
        user2.setPassword("User123456");
        user2.setLeader(TM);
        user2.setEnable(true);


        UserDTO user3 = new UserDTO();
        user3.setType(UserType.USER);
        user3.setEmail("alex.bonteanu@trainup.com");
        user3.setFirstName("Alex");
        user3.setLastName("Bonteanu");
        user3.setPassword("User123456");
        user3.setLeader(TM);
        user3.setEnable(true);

        UserDTO user4 = new UserDTO();
        user4.setType(UserType.USER);
        user4.setEmail("mihai.iamandei@trainup.com");
        user4.setFirstName("Mihai");
        user4.setLastName("Iamandei");
        user4.setPassword("User123456");
        user4.setLeader(TM);
        user4.setEnable(true);

        UserDTO user5 = new UserDTO();
        user5.setType(UserType.USER);
        user5.setEmail("vlad.manea@trainup.com");
        user5.setFirstName("Vlad");
        user5.setLastName("Manea");
        user5.setPassword("User123456");
        user5.setLeader(TM);
        user5.setEnable(true);


        synchronized (userService) {
            userService.addUser(admin);
            userService.addUser(pm);
            userService.addUser(pm1);
            userService.addUser(pm2);
            userService.addUser(tm);
            userService.addUser(user);
            userService.addUser(user1);
            userService.addUser(user2);
            userService.addUser(user3);
            userService.addUser(user4);
            userService.addUser(user5);
        }

        CourseDTO courseDTO02 = new CourseDTO();
        courseDTO02.setActualCapacity(1);
        courseDTO02.setCapacity(15);
        courseDTO02.setCourseName("Welcome to DBTS");
        courseDTO02.setStartDate(LocalDate.now().plusDays(3));
        courseDTO02.setEndDate(LocalDate.now().plusMonths(2));
        courseDTO02.setProjectManager(PM_PROC);
        courseDTO02.setDomain(Domains.RCA);
        courseDTO02.setType(CourseType.PROCESS);
        courseDTO02.setTimeInterval("10:00-16:00");

        CourseDTO courseDTO01 = new CourseDTO();
        courseDTO01.setActualCapacity(1);
        courseDTO01.setCapacity(15);
        courseDTO01.setCourseName("System architecture");
        courseDTO01.setStartDate(LocalDate.now().plusDays(1));
        courseDTO01.setEndDate(LocalDate.now().minusWeeks(4));
        courseDTO01.setProjectManager(PM_SOFT);
        courseDTO01.setDomain(Domains.PWCC);
        courseDTO01.setType(CourseType.SOFT);
        courseDTO01.setTimeInterval("11:00-17:00");

        CourseDTO courseDTO0 = new CourseDTO();
        courseDTO0.setActualCapacity(10);
        courseDTO0.setCapacity(10);
        courseDTO0.setCourseName("Introduction to agile project management");
        courseDTO0.setStartDate(LocalDate.now().minusMonths(2));
        courseDTO0.setEndDate(LocalDate.now().minusWeeks(1));
        courseDTO0.setProjectManager(PM_SOFT);
        courseDTO0.setDomain(Domains.RCA);
        courseDTO0.setType(CourseType.SOFT);
        courseDTO0.setTimeInterval("19:00-24:00");

        CourseDTO courseDTO1 = new CourseDTO();
        courseDTO1.setActualCapacity(10);
        courseDTO1.setCapacity(10);
        courseDTO1.setCourseName("Object oriented programming");
        courseDTO1.setStartDate(LocalDate.now().plusMonths(8));
        courseDTO1.setEndDate(LocalDate.now().plusMonths(12));
        courseDTO1.setProjectManager(PM_TECH);
        courseDTO1.setDomain(Domains.PWCC);
        courseDTO1.setType(CourseType.TECH);
        courseDTO1.setTimeInterval("19:00-24:00");

        CourseDTO courseDTO2 = new CourseDTO();
        courseDTO2.setActualCapacity(20);
        courseDTO2.setCapacity(20);
        courseDTO2.setCourseName("Introduction to java");
        courseDTO2.setStartDate(LocalDate.now().minusWeeks(5));
        courseDTO2.setEndDate(LocalDate.now().minusWeeks(2));
        courseDTO2.setProjectManager(PM_TECH);
        courseDTO2.setDomain(Domains.GTB);
        courseDTO2.setType(CourseType.TECH);
        courseDTO2.setTimeInterval("11:00-14:00");

        CourseDTO courseDTO3 = new CourseDTO();
        courseDTO3.setActualCapacity(30);
        courseDTO3.setCapacity(30);
        courseDTO3.setCourseName("Java Advanced");
        courseDTO3.setStartDate(LocalDate.now().plusDays(10));
        courseDTO3.setEndDate(LocalDate.now().plusMonths(2));
        courseDTO3.setProjectManager(PM_SOFT);
        courseDTO3.setDomain(Domains.RCA);
        courseDTO3.setType(CourseType.SOFT);
        courseDTO3.setTimeInterval("11:00-15:00");

        CourseDTO courseDTO4 = new CourseDTO();
        courseDTO4.setActualCapacity(10);
        courseDTO4.setCapacity(10);
        courseDTO4.setCourseName("Linux and unix system administration ");
        courseDTO4.setStartDate(LocalDate.now().plusYears(4));
        courseDTO4.setEndDate(LocalDate.now().plusYears(5));
        courseDTO4.setProjectManager(PM_TECH);
        courseDTO4.setDomain(Domains.NFR);
        courseDTO4.setType(CourseType.TECH);
        courseDTO4.setTimeInterval("12:00-16:00");


        CourseDTO courseDTO5 = new CourseDTO();
        courseDTO5.setActualCapacity(10);
        courseDTO5.setCapacity(10);
        courseDTO5.setCourseName("C# and .net");
        courseDTO5.setStartDate(LocalDate.now().plusDays(15));
        courseDTO5.setEndDate(LocalDate.now().plusMonths(3));
        courseDTO5.setProjectManager(PM_TECH);
        courseDTO5.setDomain(Domains.PWCC);
        courseDTO5.setType(CourseType.TECH);
        courseDTO5.setTimeInterval("12:00-16:00");

        CourseDTO courseDTO6 = new CourseDTO();
        courseDTO6.setActualCapacity(10);
        courseDTO6.setCapacity(10);
        courseDTO6.setCourseName("Basics of C++");
        courseDTO6.setStartDate(LocalDate.now().minusDays(10));
        courseDTO6.setEndDate(LocalDate.now().minusDays(9));
        courseDTO6.setProjectManager(PM_TECH);
        courseDTO6.setDomain(Domains.GTB);
        courseDTO6.setType(CourseType.TECH);
        courseDTO6.setTimeInterval("10:00-16:00");

        CourseDTO courseDTO7 = new CourseDTO();
        courseDTO7.setActualCapacity(10);
        courseDTO7.setCapacity(10);
        courseDTO7.setCourseName("How to show a presentation");
        courseDTO7.setStartDate(LocalDate.now().minusDays(4));
        courseDTO7.setEndDate(LocalDate.now().minusDays(3));
        courseDTO7.setProjectManager(PM_SOFT);
        courseDTO7.setDomain(Domains.PWCC);
        courseDTO7.setType(CourseType.SOFT);
        courseDTO7.setTimeInterval("10:00-13:00");

        CourseDTO courseDTO8 = new CourseDTO();
        courseDTO8.setActualCapacity(10);
        courseDTO8.setCapacity(10);
        courseDTO8.setCourseName("Waterfall vs Agile");
        courseDTO8.setStartDate(LocalDate.now().minusDays(6));
        courseDTO8.setEndDate(LocalDate.now().minusDays(15));
        courseDTO8.setProjectManager(PM_PROC);
        courseDTO8.setDomain(Domains.NFR);
        courseDTO8.setType(CourseType.PROCESS);
        courseDTO8.setTimeInterval("13:00-17:00");

        CourseDTO courseDTO9 = new CourseDTO();
        courseDTO9.setActualCapacity(10);
        courseDTO9.setCapacity(10);
        courseDTO9.setCourseName("HTML and CSS");
        courseDTO9.setStartDate(LocalDate.now().minusDays(1));
        courseDTO9.setEndDate(LocalDate.now().plusDays(10));
        courseDTO9.setProjectManager(PM_TECH);
        courseDTO9.setDomain(Domains.NFR);
        courseDTO9.setType(CourseType.TECH);
        courseDTO9.setTimeInterval("9:00-16:00");

        CourseDTO courseDTO91 = new CourseDTO();
        courseDTO91.setActualCapacity(10);
        courseDTO91.setCapacity(10);
        courseDTO91.setCourseName("Angular");
        courseDTO91.setStartDate(LocalDate.now().minusDays(5));
        courseDTO91.setEndDate(LocalDate.now().plusDays(15));
        courseDTO91.setProjectManager(PM_TECH);
        courseDTO91.setDomain(Domains.GTB);
        courseDTO91.setType(CourseType.TECH);
        courseDTO91.setTimeInterval("9:00-16:00");

        CourseDTO courseDTO92 = new CourseDTO();
        courseDTO92.setActualCapacity(10);
        courseDTO92.setCapacity(10);
        courseDTO92.setCourseName("Algorithms and Data Structure");
        courseDTO92.setStartDate(LocalDate.now().minusDays(7));
        courseDTO92.setEndDate(LocalDate.now().plusDays(5));
        courseDTO92.setProjectManager(PM_TECH);
        courseDTO92.setDomain(Domains.PWCC);
        courseDTO92.setType(CourseType.TECH);
        courseDTO92.setTimeInterval("9:00-16:00");

        CourseDTO courseDTO93 = new CourseDTO();
        courseDTO93.setActualCapacity(10);
        courseDTO93.setCapacity(10);
        courseDTO93.setCourseName("German");
        courseDTO93.setStartDate(LocalDate.now().plusYears(2));
        courseDTO93.setEndDate(LocalDate.now().plusYears(3));
        courseDTO93.setProjectManager(PM_SOFT);
        courseDTO93.setDomain(Domains.PWCC);
        courseDTO93.setType(CourseType.SOFT);
        courseDTO93.setTimeInterval("9:00-12:00");

        synchronized (courseService) {
            courseService.addCourse(courseDTO1);
            courseService.addCourse(courseDTO2);
            courseService.addCourse(courseDTO3);
            courseService.addCourse(courseDTO4);
            courseService.addCourse(courseDTO5);
            courseService.addCourse(courseDTO0);
            courseService.addCourse(courseDTO01);
            courseService.addCourse(courseDTO02);
            courseService.addCourse(courseDTO6);
            courseService.addCourse(courseDTO7);
            courseService.addCourse(courseDTO8);
            courseService.addCourse(courseDTO9);
            courseService.addCourse(courseDTO91);
            courseService.addCourse(courseDTO92);
            courseService.addCourse(courseDTO93);
        }

        List<CourseDTO> dummyCourses = new ArrayList<>();
        dummyCourses.add(courseDTO6);
        dummyCourses.add(courseDTO7);
        dummyCourses.add(courseDTO8);
        dummyCourses.add(courseDTO91);
        dummyCourses.add(courseDTO92);

        userService.findAll().stream().filter(p -> p.getType().equals(UserType.USER)).forEach(u -> {
            List<CourseDTO> courses = new ArrayList<>();
            courses.add(courseDTO9);
            u.setWaitToEnroll(courses);
            userService.saveAndFlushBack(u);
        });

        Course cc = courseService.findAllDB().stream()
                .filter(c -> c.getCourseName().equals("HTML and CSS"))
                .findFirst()
                .orElse(null);

        userService.findAllDB().stream().filter(p -> p.getType().equals(UserType.USER)).forEach(u -> {
            List<Course> courses = new ArrayList<>();
            courses.add(cc);
            u.setWaitToEnroll(courses);
            userService.saveAndFlush(u);
        });

        List<CourseDTO> arr = new ArrayList<>();
        arr.add(courseDTO5);

        UserDTO userrr = new UserDTO();
        userrr.setType(UserType.USER);
        userrr.setEmail("alex.mihai@trainup.com");
        userrr.setFirstName("Alex");
        userrr.setLastName("Mihai");
        userrr.setPassword("User123456");
        userrr.setLeader(TM);
        userrr.setEnable(true);
        userrr.setWishToEnroll(arr);

        UserDTO userrr1 = new UserDTO();
        userrr1.setType(UserType.USER);
        userrr1.setEmail("dorin.mihai@trainup.com");
        userrr1.setFirstName("Dorin");
        userrr1.setLastName("Mihai");
        userrr1.setPassword("User123456");
        userrr1.setLeader(TM);
        userrr1.setEnable(true);
        userrr1.setWishToEnroll(arr);


        UserDTO userrr2 = new UserDTO();
        userrr2.setType(UserType.USER);
        userrr2.setEmail("cosmin.mihai@trainup.com");
        userrr2.setFirstName("Cosmin");
        userrr2.setLastName("Mihai");
        userrr2.setPassword("User123456");
        userrr2.setLeader(TM);
        userrr2.setEnable(true);
        userrr2.setWishToEnroll(arr);


        synchronized (userService) {
            userService.addUser(userrr);
            userService.addUser(userrr1);
            userService.addUser(userrr2);
        }

        userService.findById(9).setCourses(dummyCourses);

    }

    @CrossOrigin(origins = "*")
    @PostMapping("/user/login")
    public UserDTO loginPage(@RequestBody UserDTO user) {
        return userService.loginService(user.getEmail(), user.getPassword());
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/user/update_users")
    public boolean updateUsers(@RequestBody List<UserDTO> users) {
        return userService.updateUsers(users);
    }

    @PostMapping("/user/findWaitByCourse")
    public List<UserDTO> findWaitByCourse(@RequestBody CourseDTO courseDTO) {
        return userService.findWaitByCourse(courseDTO);
    }


    @CrossOrigin(origins = "*")
    @PostMapping("/user/wish")
    public UserDTO wishToEnroll(@RequestBody CourseUserDTO array) {
        return userService.wishToEnroll(array.getUser(), array.getCourse());
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/user/waitToEnroll")
    public UserDTO waitToEnroll(@RequestBody CourseUserDTO array) {
        return userService.waitToEnroll(array.getUser(), array.getCourse());
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/user/findByName")
    public UserDTO findByName(@RequestParam("name") String name) {
        return userService.findAll().stream().filter(u -> u.getEmail().toLowerCase().equals(name.toLowerCase()))
                .findFirst().orElse(null);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/user/register")
    public UserDTO registerPage(@RequestBody UserDTO user) {
        return userService.addUser(user);
    }

    @GetMapping("/user/remove")
    public void removeUserByIdPage(@RequestParam("id") long id) {
        userService.removeUser(id);
    }

    @GetMapping("/user/find")
    public UserDTO findUserByIdPage(@RequestParam("id") long id) {
        return userService.findById(id);
    }

    @GetMapping("/user/findByLeader")
    public List<UserDTO> findByLeader(@RequestParam("leader") String leader) {
        return userService.findAllWithLeader(leader);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("user/refuseToEnroll")
    public UserDTO refuseToEnroll(@RequestBody CourseUserDTO array) {
        return userService.removeFromWish(array.getUser(), array.getCourse());
    }

    @CrossOrigin(origins = "*")
    @PostMapping("user/rejectFromWait")
    public UserDTO rejectFromWait(@RequestBody CourseUserDTO array) {
        return userService.rejectFromWait(array.getUser(), array.getCourse());
    }

    @CrossOrigin(origins = "*")
    @PostMapping("user/acceptFromWait")
    public UserDTO acceptFromWait(@RequestBody CourseUserDTO array) {
        return userService.acceptFromWait(array.getUser(), array.getCourse());
    }

    @CrossOrigin(origins = "*")
    @PostMapping("user/acceptAll")
    public List<UserDTO> acceptAll(@RequestBody LUsersCourse array) {
        return userService.acceptAllUsers(array.getUsers(), array.getCourse());
    }

    @CrossOrigin(origins = "*")
    @PostMapping("user/moveToAccepted")
    public UserDTO moveToAccepted(@RequestBody CourseUserDTO array) {
        return userService.swapRejectedAccepted(array.getUser(), array.getCourse());
    }


    @CrossOrigin(origins = "*")
    @PostMapping("user/moveToRejected")
    public UserDTO moveToRejected(@RequestBody CourseUserDTO array) {
        return userService.swapAcceptedRejected(array.getUser(), array.getCourse());
    }
}