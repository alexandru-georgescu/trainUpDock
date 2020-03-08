package com.trainingup.trainingupapp.service.statistics.tm_statistics;

import com.trainingup.trainingupapp.dto.UserDTO;

import java.util.List;

public interface TmStatisticsService {
    int rejected(UserDTO tm);
    int accepted(UserDTO tm);
    String teamPercentage(UserDTO tm);

    List<Integer> typeStatistic(UserDTO user);
    List<Integer> yearStatistic(UserDTO user);
}
