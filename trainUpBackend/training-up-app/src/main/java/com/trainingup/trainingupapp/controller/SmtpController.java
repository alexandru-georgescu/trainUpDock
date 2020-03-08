package com.trainingup.trainingupapp.controller;

import com.trainingup.trainingupapp.dto.UserDTO;
import com.trainingup.trainingupapp.service.smtp_service.SmtpService;
import com.trainingup.trainingupapp.service.user_service.UserService;
import com.trainingup.trainingupapp.tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin (origins = "*")
public class SmtpController {

    @Autowired
    UserService userService;

    @Autowired
    SmtpService smtpService;


    @PostMapping("trainup/reset_pass")
    @ResponseBody
    public void mail_to_reset(@RequestBody UserDTO userDTO) {
        User user = userService.findAllDB()
                .stream()
                .filter(user1 -> user1.getId() == userDTO.getId())
                .findFirst().orElse(null);

        if (user == null) {
            return;
        }

        String content ="Hi " + userDTO.getFirstName() + " " + userDTO.getLastName() + ",\n"
                 + "You recently requested to resest your password for your account.\n" +
                "Use the link below to reset it.\n"
                 + "http://localhost:8080/trainup/reset?id="+ user.getToken();
        smtpService.sendEmailTo("trainupapply@gmail.com", "Reset password", content);
    }

    @GetMapping("/trainup/validate")
    public String validateEmail(@RequestParam("id") String token) {
        List<User> users = userService.findAllDB();
        List<UserDTO> userDTOS = userService.findAll();
        User check = users.stream()
                .filter(u -> u.getToken().equals(token))
                .findFirst()
                .orElse(null);

        if (check == null) {
            return "errorPage";
        }
        users.forEach(us -> {
            if (us.getToken().equals(token)) {

                userDTOS.forEach(uss -> {
                    if (uss.getId() == us.getId()) {
                        uss.setEnable(true);
                        userService.saveAndFlushBack(uss);
                    }
                });

                us.setEnable(true);
                us.setToken(userService.generateToken());
                userService.saveAndFlush(us);
            }
        });

        return "succesRegister";
    }
}
