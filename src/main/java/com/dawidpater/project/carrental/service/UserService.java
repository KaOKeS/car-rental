package com.dawidpater.project.carrental.service;

import com.dawidpater.project.carrental.entity.Feedback;
import com.dawidpater.project.carrental.entity.User;
import com.dawidpater.project.carrental.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User save(User user){
        return userRepository.save(user);
    }
}
