package com.trainingup.trainingupapp.threads;

import com.trainingup.trainingupapp.dto.CourseDTO;
import com.trainingup.trainingupapp.dto.MailDTO;
import com.trainingup.trainingupapp.dto.UserDTO;
import com.trainingup.trainingupapp.repository.EmailRepository;
import com.trainingup.trainingupapp.service.course_service.CourseService;
import com.trainingup.trainingupapp.service.email_service.DnsRequest;
import com.trainingup.trainingupapp.service.email_service.EmailService;
import com.trainingup.trainingupapp.service.user_service.UserService;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Component
public class SmtpThread extends Thread {

    private String username = "trainupapply@gmail.com";
    private String password = "trainUp112";
    private String host = "pop.gmail.com";
    private String prot = "pop3";
    private String port = "995";
    private Store store;
    private Properties properties;
    private Session session;
    private Folder folder;


    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    EmailService emailService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    public void sendEmail(String to) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject("Message Received!");
        message.setText("Hi, \n \n Your request has been received!");

        javaMailSender.send(message);
    }

    public void sendEmail(String to, String subject, String context) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject(subject);
        message.setText(context);

        javaMailSender.send(message);
    }



    public void initPop3() {
        if (properties == null) {
            properties = new Properties();
            properties.put("mail.pop3.host", host);
            properties.put("mail.pop3.port", port);
            properties.put("mail.pop3.starttls.enable", "true");
        }

        try {
            session = Session.getDefaultInstance(properties);
            store = session.getStore("pop3s");
            store.connect(host, username, password);
            folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            try {
                synchronized (this) {
                    List<MailDTO> emails = getEmail();

                    if (emails == null) {
                        continue;
                    }

                    for (int i = 0; i < emails.size(); i++) {

                        if (emails.get(i).getSubject().split("]").length < 1 ||
                                emails.get(i).getFrom().toLowerCase().equals("trainupapply@gmail.com")) {
                            continue;
                        }

                        String[] subject = emails.get(i)
                                .getSubject()
                                .replace("[", "")
                                .replace("]", "_")
                                .split("_");

                        String sendFrom = emails.get(i).getFrom().split(">")[0].split("<")[1];

                        String courseName = subject[0];
                        String body = emails.get(i).getBody();

                        String[] pars = body.split("\n");

                        for (int j = 1; j < pars.length; j++) {
                            pars[i] = pars[i].replace("\r", "");
                        }

                        if (courseName.toLowerCase().equals(DnsRequest.HELP_REQ)) {
                                //TODO: PRIMESTE LISTA DE COMENZI PE CARE O POATE DA.
                                //TODO: DAY 2 OR 1
                            sendEmail(sendFrom, DnsRequest.HELP, emailService.help(sendFrom));
                            continue;
                        } else if (courseName.toLowerCase().equals(DnsRequest.INFO_REQ)) {
                            //TODO: PRIMESTE INFORMATII DESPRE MINE SAU ALTE REQUESTURI.
                            //TODO: DAY 1
                            //TODO: TIME 2H MAX (10:30 - 12:30)
                            sendEmail(sendFrom, DnsRequest.INFO, emailService.info(sendFrom));
                            continue;
                        } else if (courseName.toLowerCase().equals(DnsRequest.ACCEPT_ALL_REQ)) {
                                //TODO: ACCEPTA TOATE CERERIE PE CARE LE ARE.
                            sendEmail(sendFrom, DnsRequest.ACCEPT_ALL, emailService.acceptAll(sendFrom));
                            continue;
                        } else if (courseName.toLowerCase().equals(DnsRequest.REJECT_ALL_REQ)) {
                                //TODO: RESPINGE TOATE CERERILE PE CARE LE ARE
                            sendEmail(sendFrom, DnsRequest.REJECT_ALL, emailService.rejectAll(sendFrom));
                            continue;
                        } else if (courseName.toLowerCase().equals(DnsRequest.ACCEPT_REQ)) {
                                //TODO: ACCEPTA CERERILE DE LA USERI PE CARE II ARE IN BODY.
                                //TODO: DAY 1
                            sendEmail(sendFrom, DnsRequest.ACCEPT, emailService.accept(sendFrom, pars));
                            continue;
                        } else if (courseName.toLowerCase().equals(DnsRequest.REJECT_REQ)) {
                                //TODO: RESPINGE CERERILE DE LA USERI PE CARE II ARE IN BODY.
                            sendEmail(sendFrom, DnsRequest.REJECT, emailService.reject(sendFrom, pars));
                            continue;
                        } else if (courseName.toLowerCase().equals(DnsRequest.WISH_REQ)) {
                                //TODO: FACE CERERE SA SE INSCRIE LA UN ANUMIT CURS, DACA ACESTA EXISTA.
                            sendEmail(sendFrom, DnsRequest.WISH, emailService.wish(sendFrom, pars));
                            continue;
                        } else if (subject.length == 4){
                            String info = subject[1];
                            String dayInterval = subject[2];
                            String timeInterval = subject[3];

                            getUsersFromEmail(pars, courseName, dayInterval, timeInterval, sendFrom);
                        } else {
                            sendEmail(sendFrom, "Invalid request!", "INFO");
                            //TODO: COMANDA INVALIDA
                            //TODO DAY 1: TEXT + HELP REQUEST!
                        }
                    }
                }

                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public List<MailDTO> getEmail() {
        try {
            initPop3();
            Message[] messages = folder.getMessages();
            List<MailDTO> messagesArray = new ArrayList<>();
            if (messages.length == 0) {
                return null;
            }

            for (int j = 0; j < messages.length; ++j) {
                //email
                Message dummy = messages[j];
                MailDTO mail = new MailDTO();

                mail.setFrom(dummy.getFrom()[0].toString());
                mail.setSubject(dummy.getSubject());

                try {
                    String result = "";
                    Object content = dummy.getContent();
                    if (content instanceof String) {
                        mail.setBody((String) content);
                        System.out.println((String) content);
                        continue;
                    }

                    MimeMultipart mimeMultipart = (MimeMultipart) dummy.getContent();
                    int count = mimeMultipart.getCount();
                    for (int i = 0; i < count; ++i) {

                        BodyPart bodyPart = mimeMultipart.getBodyPart(i);

                        if (bodyPart.isMimeType("text/plain")) {
                            result += '\n' + "" + bodyPart.getContent();
                            break;
                        } else if (bodyPart.isMimeType("text/html")) {
                            String html = (String) bodyPart.getContent();
                            result += '\n' + Jsoup.parse(html).text();
                        }
                    }

                    mail.setBody(result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                messagesArray.add(mail);
            }
            store.close();
            return messagesArray;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        try {
            store.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getUsersFromEmail(String[] body, String courseName,
                                  String dayInterval, String timeInterval, String to) {

        try {


        List<UserDTO> serviceUsers = userService.findAll();

        CourseDTO course = courseService
                .findAll().stream()
                .filter(c -> c.getCourseName().toLowerCase().equals(courseName.toLowerCase()))
                .findFirst().orElse(null);

        if (course == null) {
            return;
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

        String[] interval = dayInterval.split("-");

        LocalDate startTime = LocalDate.parse(interval[0], dateTimeFormatter);
        LocalDate endTime = LocalDate.parse(interval[1], dateTimeFormatter);

        if (!course.getStartDate().isEqual(startTime) || !course.getEndDate().isEqual(endTime)) {
            sendEmail(to, "Error!", "Your request was not submited!" +
                    "\n Please check your course date! \n TEAM TRAINUP");
            return;
        }

        if (!course.getTimeInterval().equals(timeInterval)) {
            sendEmail(to, "Error!", "Your request was not submited!" +
                    "\n Please check your course time! \n TEAM TRAINUP");
            return ;
        }

        Arrays.stream(body).forEach(element -> {
            String newElement = element.replaceAll("\r", "");

            UserDTO user1 = serviceUsers.stream()
                    .filter(user -> user.getEmail().toLowerCase().equals(newElement.toLowerCase()))
                    .findFirst()
                    .orElse(null);

            if (user1 != null) {
                userService.waitToEnroll(user1, course);
            }
        });
            sendEmail(to);
        } catch (Exception e) {
            e.printStackTrace();
            sendEmail(to, "Error!", "Your request was not submited!" +
                    "\n Please check your request! \n TEAM TRAINUP");
        }
    }

}