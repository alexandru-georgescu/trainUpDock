package com.trainingup.trainingupapp.service.outlook_service;

import com.trainingup.trainingupapp.dto.CourseDTO;
import com.trainingup.trainingupapp.dto.UserDTO;

public interface InvitationService {

    void send(UserDTO user, CourseDTO courseDTO);
    void reject(UserDTO user, CourseDTO courseDTO);

}
