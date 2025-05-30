package com.example.berkutsanzharbot.repository;

import com.example.berkutsanzharbot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
