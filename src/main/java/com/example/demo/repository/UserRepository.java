package com.example.demo.repository;

import com.example.demo.model.UserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
@Slf4j
public class UserRepository {
    private final Map<String, UserDetails> users = new HashMap<>();

    public UserDetails save(UserDetails user) {
        log.debug("Saving user to repository: {}", user.name());
        users.put(user.name(), user);
        return user;
    }

    public Optional<UserDetails> findByName(String name) {
        log.debug("Finding user by name: {}", name);
        return Optional.ofNullable(users.get(name));
    }

    public List<UserDetails> findAll() {
        log.debug("Finding all users, count: {}", users.size());
        return new ArrayList<>(users.values());
    }

    public void deleteByName(String name) {
        log.debug("Deleting user from repository: {}", name);
        users.remove(name);
    }

    public boolean existsByName(String name) {
        return users.containsKey(name);
    }
}