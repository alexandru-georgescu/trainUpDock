package com.trainingup.trainingupapp.service.statistics.user_statistics;

import com.trainingup.trainingupapp.dto.UserDTO;

import java.util.List;

public interface UserStatisticsService {
    List<Integer> courseStatistic(UserDTO user);
    int days(UserDTO user);
    int daysLeft(UserDTO user);
}
