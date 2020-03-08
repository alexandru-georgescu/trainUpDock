package com.trainingup.trainingupapp.service.statistics.tm_statistics;

import com.trainingup.trainingupapp.dto.CourseDTO;
import com.trainingup.trainingupapp.dto.UserDTO;
import com.trainingup.trainingupapp.service.course_service.CourseService;
import com.trainingup.trainingupapp.service.user_service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class SimpleTmStatisticsService implements TmStatisticsService {
    @Autowired
    UserService userService;

    @Autowired
    CourseService courseService;

    @Override
    public int rejected(UserDTO tm) {
        return userService.findByName(tm.getEmail()).getRejected();
    }

    @Override
    public int accepted(UserDTO tm) {
        return userService.findByName(tm.getEmail()).getAccepted();
    }


    @Override
    public String teamPercentage(UserDTO tm) {
        List<UserDTO> allUsers = userService.findAllWithLeader(tm.getEmail());

        List<UserDTO> fillterUser = allUsers
                .stream()
                .filter(u -> u.getCourses().size() >= 1)
                .collect(Collectors.toList());

        String percentage = String.valueOf(1F * fillterUser.size() / allUsers.size() * 100);
        return percentage;
    }

    @Override
    public List<Integer> typeStatistic(UserDTO user) {
        List<UserDTO> team = userService.findAllWithLeader(user.getEmail());

        AtomicInteger tech = new AtomicInteger(0);
        AtomicInteger soft = new AtomicInteger(0);
        AtomicInteger process = new AtomicInteger(0);

        team.forEach(u -> {
            u.getCourses().forEach(c -> {
                switch (c.getType()) {
                    case TECH:
                        tech.set(tech.get() + 1);
                        break;
                    case SOFT:
                        soft.set(soft.get() + 1);
                        break;
                    case PROCESS:
                        process.set(process.get() + 1);
                        break;
                }
            });

        });

        List<Integer> types = new ArrayList<>();
        types.add(tech.get());
        types.add(soft.get());
        types.add(process.get());
        return types;
    }

    @Override
    public List<Integer> yearStatistic(UserDTO user) {
        List<AtomicInteger> year = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            year.add(new AtomicInteger(0));
        }

        List<UserDTO> users = userService.findAllWithLeader(user.getEmail());

        List<CourseDTO> courses = new ArrayList<>();
        users.forEach(u -> courses.addAll(u.getCourses()));

        courses.forEach(c -> {
            LocalDate start = c.getStartDate();
            LocalDate end = c.getEndDate();

            int mStart = start.getMonth().getValue();
            int mStop = end.getMonth().getValue();

            for (int i = mStart; i <= mStop; i++) {
                AtomicInteger m = year.get(i - 1);
                m.set(m.get() + 1);
            }
        });
        List<Integer> finalYear = new ArrayList<>();
        year.forEach(m -> finalYear.add(m.get()));

        return finalYear;
    }
}
