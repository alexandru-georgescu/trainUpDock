package com.trainingup.trainingupapp.controller;

import com.trainingup.trainingupapp.dto.UserDTO;
import com.trainingup.trainingupapp.service.statistics.pm_statistics.PmStatisticsService;
import com.trainingup.trainingupapp.service.statistics.tm_statistics.TmStatisticsService;
import com.trainingup.trainingupapp.service.statistics.user_statistics.UserStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class StatisticsController {

    @Autowired
    TmStatisticsService tmStatisticsService;
    
    @Autowired
    UserStatisticsService userStatisticsService;

    @Autowired
    PmStatisticsService pmStatisticsService;

    @PostMapping("/tm_statistics/rejected")
    public int rejected(@RequestBody UserDTO tm) {
        return tmStatisticsService.rejected(tm);
    }

    @PostMapping("/tm_statistics/accepted")
    public int accepted(@RequestBody UserDTO tm) {
        return tmStatisticsService.accepted(tm);
    }

    @PostMapping("/user_statistics/days")
    public int days(@RequestBody UserDTO user) {
        return userStatisticsService.days(user);
    }

    @PostMapping("/user_statistics/type_statistic")
    public List<Integer> typeStatistic(@RequestBody UserDTO tm) {
        return tmStatisticsService.typeStatistic(tm);
    }

    @PostMapping("/user_statistics/course_statistic")
    public List<Integer> courseStatistic(@RequestBody UserDTO tm) {
        return userStatisticsService.courseStatistic(tm);
    }

    @PostMapping("/user_statistics/days_left")
    public int daysLeft(@RequestBody UserDTO tm) {
        return userStatisticsService.daysLeft(tm);
    }

    @PostMapping("/tm_statistics/team_percentage")
    public String teamPercentage(@RequestBody UserDTO tm) {
        return tmStatisticsService.teamPercentage(tm);
    }

    @PostMapping("/pm_statistics/max_enrollment_domains")
    public List<String> maxEnrollmentDomains(@RequestBody UserDTO pm) {
        return pmStatisticsService.maxEnrollmentDomains(pm);
    }

    @PostMapping("/pm_statistics/course_below_50")
    public List<String> courseBelow50(@RequestBody UserDTO pm) {
        return pmStatisticsService.courseBelow50(pm);
    }

    @PostMapping("/tm_statistics/year_statistic")
    public List<Integer> yearStatistic(@RequestBody UserDTO tm) {
        return tmStatisticsService.yearStatistic(tm);
    }

}
