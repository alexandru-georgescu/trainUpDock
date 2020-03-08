package com.trainingup.trainingupapp.service.statistics.pm_statistics;

import com.trainingup.trainingupapp.dto.UserDTO;
import com.trainingup.trainingupapp.enums.Domains;

import java.util.List;

public interface PmStatisticsService {
    List<String> courseBelow50(UserDTO user);
    List<String> maxEnrollmentDomains(UserDTO user);
}
