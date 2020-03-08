package com.trainingup.trainingupapp.repository;

import com.trainingup.trainingupapp.tables.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<EmailTemplate, Long> {
}
