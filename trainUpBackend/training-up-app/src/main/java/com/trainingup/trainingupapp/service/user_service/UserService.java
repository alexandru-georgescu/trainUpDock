package com.trainingup.trainingupapp.service.user_service;

import com.trainingup.trainingupapp.dto.CourseDTO;
import com.trainingup.trainingupapp.dto.CourseUserDTO;
import com.trainingup.trainingupapp.dto.UserDTO;
import com.trainingup.trainingupapp.tables.User;

import java.util.List;

public interface UserService {
    boolean checkExistWish(UserDTO user, CourseDTO course);
    List<UserDTO> findAll();
    UserDTO findById(long id);
    User findByIdDB(long id);
    User findByName(String name);
    UserDTO findByNameDTO(String name);
    void updateAccepted(UserDTO user);
    void updateRejected(UserDTO user);
    UserDTO addUser(UserDTO user);
    void removeUser(long id);
    UserDTO loginService(String username, String password);
    boolean validate(String email, String password);
    UserDTO wishToEnroll(UserDTO user, CourseDTO course);
    UserDTO waitToEnroll(UserDTO user, CourseDTO course);
    List<UserDTO> findAllWithLeader(String leader);
    List<User> findAllDB();
    UserDTO removeFromWish(UserDTO user, CourseDTO course);
    void saveAndFlush(User user);
    void saveAndFlushBack(UserDTO user);
    String generateToken();
    List<UserDTO> findWaitByCourse(CourseDTO courseDTO);
    UserDTO acceptFromWait(UserDTO user, CourseDTO course);
    UserDTO rejectFromWait(UserDTO user, CourseDTO course);
    boolean updateUsers(List<UserDTO> users);
    List<UserDTO> acceptAllUsers(List<UserDTO> users, CourseDTO course);
    UserDTO swapAcceptedRejected(UserDTO user, CourseDTO course);
    UserDTO swapRejectedAccepted(UserDTO user, CourseDTO course);
}
