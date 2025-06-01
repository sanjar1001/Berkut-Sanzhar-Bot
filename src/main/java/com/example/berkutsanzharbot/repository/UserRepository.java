package com.example.berkutsanzharbot.repository;

import com.example.berkutsanzharbot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByTelegramToken(String text);

    Optional<User> findByChatId(String chatId);
}
