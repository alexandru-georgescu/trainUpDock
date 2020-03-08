package com.trainingup.trainingupapp.service.statistics.pm_statistics;

import com.trainingup.trainingupapp.dto.CourseDTO;
import com.trainingup.trainingupapp.dto.UserDTO;
import com.trainingup.trainingupapp.enums.Domains;
import com.trainingup.trainingupapp.service.course_service.CourseService;
import com.trainingup.trainingupapp.service.user_service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class SimplePmStatisticsService implements PmStatisticsService {
    @Autowired
    UserService userService;

    @Autowired
    CourseService courseService;

    @Override
    public List<String> courseBelow50(UserDTO user) {
        List<CourseDTO> courses = courseService.findByPm(user);
        List<String> names = new ArrayList<>();

        courses.forEach(c -> {
            if (c.getActualCapacity() > c.getCapacity()/2) {
                names.add(c.getCourseName());
            }
        });
        return names;
    }

    @Override
    public List<String> maxEnrollmentDomains(UserDTO user) {
        List<CourseDTO> courseDTOS = courseService.findByPm(user);

        AtomicInteger RCA = new AtomicInteger(0);
        AtomicInteger NFR = new AtomicInteger(0);
        AtomicInteger GTB = new AtomicInteger(0);
        AtomicInteger PWCC = new AtomicInteger(0);


        courseDTOS.forEach(c -> {
            switch (c.getDomain()) {
                case RCA: RCA.set(RCA.get() + 1); break;
                case GTB: GTB.set(GTB.get() + 1); break;
                case NFR: NFR.set(NFR.get() + 1); break;
                case PWCC: PWCC.set(PWCC.get() + 1); break;
            }
        });

        int sum = RCA.get() + NFR.get() + GTB.get() + PWCC.get();

        List<String> domains = new ArrayList<>();
        if (sum == 0) {
            domains.add(String.valueOf(0));
            domains.add(String.valueOf(0));
            domains.add(String.valueOf(0));
            domains.add(String.valueOf(0));
            return domains;
        }
        domains.add(String.valueOf((1F * RCA.get()/sum) * 100));
        domains.add(String.valueOf((1F * NFR.get()/sum) * 100));
        domains.add(String.valueOf((1F * GTB.get()/sum) * 100));
        domains.add(String.valueOf((1F * PWCC.get()/sum) * 100));
        return domains;
    }
}
