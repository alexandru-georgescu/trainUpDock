package com.trainingup.trainingupapp.service.email_service;

import com.trainingup.trainingupapp.convertor.UserConvertor;
import com.trainingup.trainingupapp.dto.CourseDTO;
import com.trainingup.trainingupapp.dto.UserDTO;
import com.trainingup.trainingupapp.enums.UserType;
import com.trainingup.trainingupapp.repository.EmailRepository;
import com.trainingup.trainingupapp.service.course_service.CourseService;
import com.trainingup.trainingupapp.service.outlook_service.InvitationService;
import com.trainingup.trainingupapp.service.user_service.UserService;
import com.trainingup.trainingupapp.tables.EmailTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class SimpleEmailService implements EmailService {

    @Autowired
    EmailRepository emailRepository;

    @Autowired
    CourseService courseService;

    @Autowired
    UserService userService;

    @Autowired
    InvitationService invitationService;

    @Override
    public EmailTemplate getUser(String email) {
        List<EmailTemplate> users = emailRepository.findAll();

        EmailTemplate user = users.stream()
                .filter(u -> u.getEmail().toLowerCase().equals(email))
                .findFirst()
                .orElse(null);

        return user;
    }

    @Override
    public String info(String email) {
        EmailTemplate user = getUser(email);

        System.out.println(user.getTrainUpEmail());

        if (user == null) {
            return "Invalid email";
        }

        String info = "Informations : \n\n\n";

        if (user.getUserType() == UserType.USER) {

            UserDTO userDTO = UserConvertor.convertToUserDTO(userService.findByName(user.getTrainUpEmail()));

            List<CourseDTO> availableCourses = courseService.findFuture(userDTO);

            for (int i = 0; i < availableCourses.size(); i++) {
                CourseDTO courseDTO = availableCourses.get(i);
                String format = "Course name : "
                        + courseDTO.getCourseName()
                        + "\n"
                        + "Start date : "
                        + courseDTO.getStartDate()
                        + "\n"
                        + "End date : "
                        + courseDTO.getEndDate()
                        + "\n"
                        + "Duration : "
                        + courseDTO.getTimeInterval()
                        + "\n"
                        + "Remained Capacity : "
                        + courseDTO.getActualCapacity()
                        + "\n"
                        + "Project Manager : "
                        + courseDTO.getProjectManager()
                        + "\n\n\n";

                info += format;
            }
            return info;
        }

        if (user.getUserType() == UserType.TM) {
            List<UserDTO> usersWithWish = userService.findAll()
                    .stream()
                    .filter(u -> !u.getWishToEnroll().isEmpty())
                    .filter(u -> u.getLeader().equals(user.getTrainUpEmail()))
                    .collect(Collectors.toList());

            for (int i = 0; i < usersWithWish.size(); i++) {
                UserDTO userDTO = usersWithWish.get(i);
                String format = "User name : "
                        + userDTO.getFirstName() + " " + userDTO.getLastName()
                        + "\n"
                        + "Email : "
                        + userDTO.getEmail()
                        + "\n"
                        + "Courses on the wish list : \n\n";
                List<CourseDTO> wishCourses = userDTO.getWishToEnroll();

                String wishCourse;
                for (int j = 0; j < wishCourses.size(); j++) {
                    CourseDTO courseDTO = wishCourses.get(j);
                    wishCourse = "Course name : "
                            + courseDTO.getCourseName()
                            + "\n"
                            + "Start date : "
                            + courseDTO.getStartDate()
                            + "\n"
                            + "End date : "
                            + courseDTO.getEndDate()
                            + "\n"
                            + "Duration : "
                            + courseDTO.getTimeInterval()
                            + "\n"
                            + "Remained Capacity : "
                            + courseDTO.getActualCapacity()
                            + "\n"
                            + "Project Manager : "
                            + courseDTO.getProjectManager()
                            + "\n\n";
                    format += wishCourse;

                }


                info += format;
            }

            return info;

        }

        if (user.getUserType() == UserType.PMSOFT || user.getUserType() == UserType.PMTECH || user.getUserType() == UserType.PMPROC) {
            List<CourseDTO> courses = courseService
                    .findByPm(UserConvertor.convertToUserDTO(userService.findByName(user.getTrainUpEmail())));

            List<UserDTO> usersWithWait = new ArrayList<>();
            List<UserDTO> allUsers = userService.findAll();

            for (int i = 0; i < allUsers.size(); i++) {
                for (int j = 0; j < courses.size(); j++) {
                    if (allUsers.get(i).getWaitToEnroll().contains(courses.get(j))) {
                        usersWithWait.add(allUsers.get(i));
                        break;
                    }
                }
            }

            String waitCourse;
            for (int j = 0; j < courses.size(); j++) {
                String format = "";
                CourseDTO courseDTO = courses.get(j);
                waitCourse = "Course name : "
                        + courseDTO.getCourseName()
                        + "\n"
                        + "Start date : "
                        + courseDTO.getStartDate()
                        + "\n"
                        + "End date : "
                        + courseDTO.getEndDate()
                        + "\n"
                        + "Duration : "
                        + courseDTO.getTimeInterval()
                        + "\n"
                        + "Remained Capacity : "
                        + courseDTO.getActualCapacity()
                        + "\n"
                        + "Project Manager : "
                        + courseDTO.getProjectManager()
                        + "\n\n";
                format += waitCourse;

                format += "Users : \n";
                for (int i = 0; i < usersWithWait.size(); i++) {
                    UserDTO userDTO = usersWithWait.get(i);
                    format+= userDTO.getFirstName() + " " + userDTO.getLastName()
                            + "\n"
                            + "Email : "
                            + userDTO.getEmail()
                            + "\n\n";


                }
                info += format;


            }



            return info;

        }
        return null;
    }

    @Override
    public String wish(String email, String[] coursesName) {
        EmailTemplate user = getUser(email);
        if (user == null || !user.getUserType().equals(UserType.USER)) {
            return "Invalid email";
        }

        UserDTO userDTO = userService.findByNameDTO(user.getTrainUpEmail());

        if (userDTO == null) {
            return "Invalid Email";
        }
        List<CourseDTO> courses = new ArrayList<>();

        final AtomicReference<String>[] ret = new AtomicReference[]{new AtomicReference<>("Courses: \n")};

        Arrays.stream(coursesName)
                .forEach(course -> {
                    if (!course.equals("")) {
                        String name = course.replaceAll("\r", "");
                        CourseDTO courseDTO = courseService.findByNameDTO(name);
                        if (courseDTO != null) {
                            if (userService.checkExistWish(userDTO, courseDTO)) {
                                ret[0].set(ret[0].get() + name + ": EXIST WISH!\n");
                            } else {
                                courses.add(courseDTO);
                                ret[0].set(ret[0].get() + name + ": SUCCESS WISH!\n");
                            }
                        } else {
                            ret[0].set(ret[0].get() + name + ": FAIL WISH!\n");
                        }
                    }
                });

        try {

            courses.forEach(c -> {
                userService.wishToEnroll(userDTO, c);
            });
        } catch (Exception c) {
            c.printStackTrace();
        }

        return ret[0].get();
    }

    @Override
    public String accept(String email, String[] body) {
        EmailTemplate user = getUser(email);
        if (user == null ||
                !(user.getUserType().equals(UserType.TM)
                        || user.getUserType().equals(UserType.PMTECH)
                        || user.getUserType().equals(UserType.PMSOFT)
                        || user.getUserType().equals(UserType.PMPROC))) {
            return "Invalid email";
        }


        final AtomicReference<String>[] ret = new AtomicReference[]{new AtomicReference<>("")};

        Arrays.stream(body).forEach(line -> {
            String newLine = line.replaceAll("\r", "");
            String[] newBody = newLine.split(" ");

            if (newBody.length != 2) {
                ret[0].set(ret[0].get() + "Request: " + line + "\n");
            } else {
                UserDTO userDTO = userService.findByNameDTO(newBody[0]);
                CourseDTO courseDTO = courseService.findByNameDTO(newBody[1]);
                if (userDTO == null && courseDTO == null) {
                    ret[0].set(ret[0].get() + "Invalid user: " + userDTO.getEmail()
                            + " and course: " + courseDTO.getCourseName() + "\n");
                } else if (userDTO == null) {
                    ret[0].set(ret[0].get() + "Invalid user: " + userDTO.getEmail() + "\n");
                } else if (courseDTO == null) {
                    ret[0].set(ret[0].get() + "Invalid course: " + courseDTO.getCourseName() + "\n");
                } else {
                    if (user.getUserType().equals(UserType.TM)) {
                        UserDTO finalUser = userService.waitToEnroll(userDTO, courseDTO);

                        if (finalUser == null) {
                            ret[0].set(ret[0].get() + "Fail enroll for: " + userDTO.getEmail() + " at " + courseDTO.getCourseName() + "\n");
                        } else {
                            ret[0].set(ret[0].get() + "Success enroll for: " + userDTO.getEmail() + " at " + courseDTO.getCourseName() + "\n");
                        }

                    } else if (user.getUserType().equals(UserType.PMPROC)
                            || user.getUserType().equals(UserType.PMTECH)
                            || user.getUserType().equals(UserType.PMSOFT)) {
                        UserDTO finalUser = userService.acceptFromWait(userDTO, courseDTO);
                        if (finalUser == null) {
                            ret[0].set(ret[0].get() + "Fail enroll for: " + userDTO.getEmail() + " at " + courseDTO.getCourseName() + "\n");
                        } else {
                            ret[0].set(ret[0].get() + "Success enroll for: " + userDTO.getEmail() + " at " + courseDTO.getCourseName() + "\n");
                        }
                    }
                }
            }
        });

        return ret[0].get();
    }

    @Override
    public String reject(String email, String[] body) {
        EmailTemplate user = getUser(email);
        if (user == null ||
                !(user.getUserType().equals(UserType.TM)
                        || user.getUserType().equals(UserType.PMTECH)
                        || user.getUserType().equals(UserType.PMSOFT)
                        || user.getUserType().equals(UserType.PMPROC))) {
            return "Invalid email";
        }


        final AtomicReference<String>[] ret = new AtomicReference[]{new AtomicReference<>("")};

        Arrays.stream(body).forEach(line -> {
            String newLine = line.replaceAll("\r", "");
            String[] newBody = newLine.split(" ");

            if (newBody.length != 2) {
                ret[0].set(ret[0].get() + "Request: " + line + "\n");
            } else {
                UserDTO userDTO = userService.findByNameDTO(newBody[0]);
                CourseDTO courseDTO = courseService.findByNameDTO(newBody[1]);
                if (userDTO == null && courseDTO == null) {
                    ret[0].set(ret[0].get() + "Invalid user: " + userDTO.getEmail()
                            + " and course: " + courseDTO.getCourseName() + "\n");
                } else if (userDTO == null) {
                    ret[0].set(ret[0].get() + "Invalid user: " + userDTO.getEmail() + "\n");
                } else if (courseDTO == null) {
                    ret[0].set(ret[0].get() + "Invalid course: " + courseDTO.getCourseName() + "\n");
                } else {
                    if (user.getUserType().equals(UserType.TM)) {
                        UserDTO finalUser = userService.removeFromWish(userDTO, courseDTO);

                        if (finalUser == null) {
                            ret[0].set(ret[0].get() + "Fail reject for: " + userDTO.getEmail() + " at " + courseDTO.getCourseName() + "\n");
                        } else {
                            ret[0].set(ret[0].get() + "Success reject for: " + userDTO.getEmail() + " at " + courseDTO.getCourseName() + "\n");
                        }

                    } else if (user.getUserType().equals(UserType.PMPROC)
                            || user.getUserType().equals(UserType.PMTECH)
                            || user.getUserType().equals(UserType.PMSOFT)) {
                        UserDTO finalUser = userService.rejectFromWait(userDTO, courseDTO);
                        if (finalUser == null) {
                            ret[0].set(ret[0].get() + "Fail reject for: " + userDTO.getEmail() + " at " + courseDTO.getCourseName() + "\n");
                        } else {
                            ret[0].set(ret[0].get() + "Success reject for: " + userDTO.getEmail() + " at " + courseDTO.getCourseName() + "\n");
                        }
                    }
                }
            }
        });

        return ret[0].get();
    }

    @Override
    public String acceptAll(String email) {
        EmailTemplate user = getUser(email);
        if (user == null ||
                !(user.getUserType().equals(UserType.TM)
                        || user.getUserType().equals(UserType.PMTECH)
                        || user.getUserType().equals(UserType.PMSOFT)
                        || user.getUserType().equals(UserType.PMPROC))) {
            return "Invalid email";
        }

        List<UserDTO> users = new ArrayList<>();
        users.addAll(userService.findAllWithLeader(user.getTrainUpEmail()));

        final AtomicReference<String>[] ret = new AtomicReference[]{new AtomicReference<>("")};

        if (users.size() == 0) {
            return "Nothing to accept!";
        }

        if (user.getUserType().equals(UserType.TM)) {
            users.forEach(u -> {
                List<CourseDTO> courses = new ArrayList<>();
                courses.addAll(u.getWishToEnroll());
                courses.forEach(c -> {
                    UserDTO finalUser = userService.waitToEnroll(u, c);
                    if (finalUser == null) {
                        ret[0].set(ret[0].get() + "Fail enroll for: " + u.getEmail() +" at " + c.getCourseName() +"\n");
                    } else {
                        ret[0].set(ret[0].get() + "Success enroll for: " + u.getEmail() + " at " + c.getCourseName() + "\n");
                    }
                });
            });
        }


        if (user.getUserType().equals(UserType.PMSOFT)
                || user.getUserType().equals(UserType.PMPROC)
                || user.getUserType().equals(UserType.PMTECH)) {
            userService.findAllWithLeader(users.get(0).getEmail()).stream().forEach(u -> {
                List<CourseDTO> courses = new ArrayList<>();
                courses.addAll(u.getWaitToEnroll()
                        .stream()
                        .filter(c -> c.getProjectManager().equals(user.getTrainUpEmail()))
                        .collect(Collectors.toList()));
                courses.forEach(c -> {
                    UserDTO finalUser = userService.acceptFromWait(u, c);
                    if (finalUser == null) {
                        ret[0].set(ret[0].get() + "Fail enroll for: " + u.getEmail() + " at " + c.getCourseName() + "\n");
                    } else {
                        ret[0].set(ret[0].get() + "Success enroll for: " + u.getEmail() + " at " + c.getCourseName() + "\n");
                    }
                });
            });
        }

        return ret[0].get();
    }

    @Override
    public String rejectAll(String email) {
        EmailTemplate user = getUser(email);
        if (user == null ||
                !(user.getUserType().equals(UserType.TM)
                        || user.getUserType().equals(UserType.PMTECH)
                        || user.getUserType().equals(UserType.PMSOFT)
                        || user.getUserType().equals(UserType.PMPROC))) {
            return "Invalid email";
        }

        List<UserDTO> users = new ArrayList<>();
        users.addAll(userService.findAllWithLeader(user.getTrainUpEmail()));

        final AtomicReference<String>[] ret = new AtomicReference[]{new AtomicReference<>("")};

        if (users.size() == 0) {
            return "Nothing to accept!";
        }

        if (user.getUserType().equals(UserType.TM)) {
            users.stream().forEach(u -> {
                List<CourseDTO> courses = new ArrayList<>();
                courses.addAll(u.getWishToEnroll());
                courses.forEach(c -> {
                    UserDTO finalUser = userService.removeFromWish(u, c);
                    if (finalUser == null) {
                        ret[0].set(ret[0].get() + "Fail reject for: " + u.getEmail() + " at " + c.getCourseName() + "\n");
                    } else {
                        ret[0].set(ret[0].get() + "Success reject for: " + u.getEmail() + " at " + c.getCourseName() + "\n");
                    }
                });
            });
        }

        if (user.getUserType().equals(UserType.PMSOFT)
                || user.getUserType().equals(UserType.PMPROC)
                || user.getUserType().equals(UserType.PMTECH)) {
            userService.findAllWithLeader(users.get(0).getEmail()).stream().forEach(u -> {
                List<CourseDTO> courses = new ArrayList<>();
                courses.addAll(u.getWaitToEnroll()
                        .stream()
                        .filter(c -> c.getProjectManager().equals(user.getTrainUpEmail()))
                        .collect(Collectors.toList()));
                courses.forEach(c -> {
                    UserDTO finalUser = userService.rejectFromWait(u, c);
                    if (finalUser == null) {
                        ret[0].set(ret[0].get() + "Fail reject for: " + u.getEmail() + " at " + c.getCourseName() + "\n");
                    } else {
                        ret[0].set(ret[0].get() + "Success reject for: " + u.getEmail() + " at " + c.getCourseName() + "\n");
                    }
                });
            });
        }

        return ret[0].get();
    }

    @Override
    public String help(String email) {
        EmailTemplate user = getUser(email);
        if (user == null) {
            return "Invalid email";
        }
        String help = "As a ";

        if (user.getUserType() == UserType.USER) {

            help += "user you have the next possible requests: \n\n"
                    + "Ask for possible courses that you can attend to\n"
                    + "To do so, in the subject of the email please write : info\n\n"
                    + "For wishing to enroll to certain courses please write in the subject : wish\n"
                    + "In the body please write on new lines each course name that you wish to attend too";

            return help;
        }
        if (user.getUserType() == UserType.TM) {

            help += "TM you have the next possible requests: \n\n"
                    + "Ask for a list of your team members and the courses they wish to attend too\n"
                    + "To do so, in the subject of the email please write : info\n\n"
                    + "To accept certain users to enroll to a certain course please write in the subject : accept\n"
                    + "Also, in the body of the email write on the first line the name of the course and after that on each new line the emails of the users you wish to enroll\n\n"
                    + "To reject certain users to enroll to a certain course please write in the subject : reject\n"
                    + "Also, in the body of the email write on the first line the name of the course and after that on each new line the emails of the users you wish to reject\n\n"
                    + "For accepting all users of your team at all the courses they wish to attend too write in the subject : acceptall\n\n"
                    + "For rejecting all users of your team at all the courses they wish to attend too write in the subject : rejectall\n";

            return help;
        }

        if (user.getUserType() == UserType.PMPROC || user.getUserType() == UserType.PMTECH || user.getUserType() == UserType.PMPROC) {

            help += "PM you have the next possible requests: \n\n"
                    + "Ask for a list of users that are in waiting to be accepted or rejected to your courses\n"
                    + "To do so, in the subject of the email please write : info\n\n"
                    + "To accept certain users to a certain course please write in the subject : accept\n"
                    + "Also, in the body of the email write on the first line the name of the course and after that on each new line the emails of the users you wish to enroll\n\n"
                    + "To reject certain users to a certain course please write in the subject : reject\n"
                    + "Also, in the body of the email write on the first line the name of the course and after that on each new line the emails of the users you wish to reject\n\n"
                    + "For accepting all users to all the courses that you are having write in the subject : acceptall\n\n"
                    + "For rejecting all users to all the courses that you are having write in the subject : rejectall\n";

            return help;
        }
        return null;
    }
}