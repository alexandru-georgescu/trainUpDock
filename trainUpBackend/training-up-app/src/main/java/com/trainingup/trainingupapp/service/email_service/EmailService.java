package com.trainingup.trainingupapp.service.email_service;

import com.trainingup.trainingupapp.tables.EmailTemplate;

public interface EmailService {
    EmailTemplate getUser(String email);

    //TODO: DAY 1
    String info(String email);
    String wish(String email, String[] coursesName);
    String accept(String email, String[] users);
    String reject(String email, String[] users);
    String acceptAll(String email);
    String rejectAll(String email);

    //TODO: DAY 2 OR D1
    String help(String email);
    //TODO..
}
