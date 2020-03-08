package com.trainingup.trainingupapp.service.smtp_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SimpleSmtpService implements SmtpService {

   @Autowired
   private JavaMailSender sender;

   public void sendEmailTo(String to, String subject, String content) {
       new Thread(() -> {
           synchronized (this) {
               SimpleMailMessage message = new SimpleMailMessage();
               message.setTo(to);
               message.setSubject(subject);
               message.setText(content);
               sender.send(message);
           }
       }).start();
   }

   public void sendValidateEmail (String to, String token, String name, String email) {

       new Thread(() -> {
           synchronized (this) {
               SimpleMailMessage message = new SimpleMailMessage();
               message.setTo(to);
               message.setSubject("Account Confirmation");

               message.setText("Hello \n \n"
                       + "Dear " + name + ",\n" +
                       "\n" +
                       "Thank you for registering to TrainUp platform !\n"

                       + "To activate your account please click the link below: \n \n"
                       + "http://127.0.0.1:8080/trainup/validate?id=" + token
                       + "\n\n\n"
                       + "You registered with this email: "+ email + ".\n" +
                       "\n" +
                       "If you forgot your password, simply press \"Forgot password\" and you'll be prompted to reset it.\n" +
                       "\n" +
                       "If you have any questions leading up to the event, feel free to reply to this email.\n\n\n"
                       + "Kind Regards,\n" +
                       "TrainUp Staff\n" +
                       "trainUpApply@gmail.com");


               sender.send(message);
           }
       }).start();
   }
}
