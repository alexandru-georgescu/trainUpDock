package com.trainingup.trainingupapp.threads;

import com.trainingup.trainingupapp.service.user_service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.LocalDate;

public class TokenThread extends Thread {

    @Autowired
    UserService userService;

    public void run ()
    {
        while(true) {
            userService.findAllDB()
                    .stream().forEach(u -> {
                if (!u.isEnable() && (Duration.between(LocalDate.now(),
                        u.getDateOfRegistration()).toHours() > 24))
                    userService.removeUser(u.getId());
            });

            try {
                Thread.sleep(3600000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}