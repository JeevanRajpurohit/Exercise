package com.example.ExpenseAndIncomeTracker.service.ServiceImplementation;

import com.example.ExpenseAndIncomeTracker.dto.LoginDto;
import com.example.ExpenseAndIncomeTracker.dto.UserRegistrationDto;
import com.example.ExpenseAndIncomeTracker.exception.EntityNotFoundException;
import com.example.ExpenseAndIncomeTracker.model.User;
import com.example.ExpenseAndIncomeTracker.repository.UserRepository;
import com.example.ExpenseAndIncomeTracker.service.AuthService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public User registerUser(UserRegistrationDto registrationDTO) {
        logger.info("Registering new user with username: {}", registrationDTO.getUsername());
        User user = modelMapper.map(registrationDTO, User.class);
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User authenticateUser(LoginDto loginDTO) {
        logger.info("Authenticating user with username: {}", loginDTO.getUsername());
        Optional<User> userOptional = userRepository.findByUsername(loginDTO.getUsername());

        if (userOptional.isEmpty()) {
            logger.error("User not found with username: {}", loginDTO.getUsername());
            throw new EntityNotFoundException("User not found");
        }

        User user = userOptional.get();
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            logger.error("Invalid password for username: {}", loginDTO.getUsername());
            throw new EntityNotFoundException("Invalid credentials");
        }

        logger.info("User authenticated successfully: {}", loginDTO.getUsername());
        return user;
    }
}