package com.trainingup.trainingupapp.service.outlook_service;

import com.trainingup.trainingupapp.dto.CourseDTO;
import com.trainingup.trainingupapp.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.util.Properties;

@Service
public class SimpleInvitationService implements InvitationService {

    private String username = "trainupapply@gmail.com";
    private String password = "trainUp112";
    private String host = "pop.gmail.com";
    private String prot = "pop3";
    private String port = "995";

    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public void send(UserDTO user, CourseDTO courseDTO) {

        new Thread(() -> {
            synchronized (this) {
                MimeMessage message = javaMailSender.createMimeMessage();

                try {
                    message.addHeaderLine("method=REQUEST");
                    message.addHeaderLine("charset=UTF-8");
                    message.setSubject("TrainUp Course Meeting");

                    message.setFrom(new InternetAddress("trainupapply@gmail.com"));
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress("trainUpApply@gmail.com"));
                    message.setSubject("TrainUp Course Meeting");

                } catch (MessagingException e) {
                    e.printStackTrace();
                }

                StringBuffer sb = new StringBuffer();

                StringBuffer buffer = sb.append("BEGIN:VCALENDAR\n" +
                        "PRODID:-//Microsoft Corporation//Outlook 9.0 MIMEDIR//EN\n" +
                        "VERSION:2.0\n" +
                        "METHOD:REQUEST\n" +
                        "BEGIN:VEVENT\n" +
                        "ATTENDEE;ROLE=REQ-PARTICIPANT;RSVP=TRUE:MAILTO:trainUpApply@gmail.com\n" +
                        "ORGANIZER:MAILTO:trainUpApply@gmail.com\n" +
                        "DTSTART:" + courseDTO.getStartDate() + "T053000Z\n" +
                        "DTEND:" + courseDTO.getEndDate() + "T060000Z\n" +
                        "LOCATION:TrainUp.srl\n" +
                        "TRANSP:OPAQUE\n" +
                        "SEQUENCE:0\n" +
                        "UID:040000008200E00074C5B7101A82E00800000000002FF466CE3AC5010000000000000000100\n" +
                        " 000004377FE5C37984842BF9440448399EB02\n" +
                        "DTSTAMP:" + courseDTO.getStartDate() + "T120102Z\n" +
                        "CATEGORIES:Meeting\n" +
                        "DESCRIPTION: TrainUp Course Meeting for " + courseDTO.getCourseName() + ".\n\n" +
                        "SUMMARY:TrainUp Meeting for " + user.getFirstName() + "\n" +
                        "PRIORITY:5\n" +
                        "CLASS:PUBLIC\n" +
                        "BEGIN:VALARM\n" +
                        "TRIGGER:PT1440M\n" +
                        "ACTION:DISPLAY\n" +
                        "DESCRIPTION:Reminder\n" +
                        "END:VALARM\n" +
                        "END:VEVENT\n" +
                        "END:VCALENDAR");

                BodyPart messageBodyPart = new MimeBodyPart();

                try {
                    messageBodyPart.setHeader("Content-Class", "urn:content-classes:calendarmessage");
                    messageBodyPart.setHeader("Content-ID", "calendar_message");
                    messageBodyPart.setDataHandler(new DataHandler(
                            new ByteArrayDataSource(buffer.toString(), "text/calendar")));

                    Multipart multipart = new MimeMultipart();

                    // Add part one
                    multipart.addBodyPart(messageBodyPart);

                    // Put parts in message
                    message.setContent(multipart);

                    // send message
                    javaMailSender.send(message);

                } catch (MessagingException | IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void reject(UserDTO user, CourseDTO courseDTO) {
        new Thread(() -> {
            synchronized (this) {
                try {
                    SimpleMailMessage message = new SimpleMailMessage();

                    message.setTo("trainupapply@gmail.com");
                    message.setSubject("Rejected!");
                    message.setText("Hi " + user.getFirstName() + " " + user.getLastName() + ","
                            + "\n \n You have been rejected from " + courseDTO.getCourseName() + "!\n");

                    javaMailSender.send(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
