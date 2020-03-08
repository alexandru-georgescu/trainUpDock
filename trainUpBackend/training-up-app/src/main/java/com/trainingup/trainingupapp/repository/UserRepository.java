package com.trainingup.trainingupapp.repository;

import com.trainingup.trainingupapp.tables.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

