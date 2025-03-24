package com.example.ExpenseAndIncomeTracker.service.ServiceImplementation;

import com.example.ExpenseAndIncomeTracker.dto.PasswordUpdateDto;
import com.example.ExpenseAndIncomeTracker.exception.EntityNotFoundException;
import com.example.ExpenseAndIncomeTracker.model.User;
import com.example.ExpenseAndIncomeTracker.repository.UserRepository;
import com.example.ExpenseAndIncomeTracker.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> findByUsername(String username) {
        logger.info("Fetching user by username: {}", username);
        return userRepository.findByUsername(username);
    }



    @Override
    public List<User> findAll() {
        logger.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public void updatePassword(String username, PasswordUpdateDto passwordUpdateDto) {
        logger.info("Updating password for user: {}", username);
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            logger.error("User not found with username: {}", username);
            throw new EntityNotFoundException("User not found with username: " + username);
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(passwordUpdateDto.getOldPassword(), user.getPassword())) {
            logger.error("Old password is incorrect for user: {}", username);
            throw new RuntimeException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(passwordUpdateDto.getNewPassword()));
        userRepository.save(user);
        logger.info("Password updated successfully for user: {}", username);
    }
}
