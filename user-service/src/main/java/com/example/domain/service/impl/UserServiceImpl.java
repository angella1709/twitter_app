package com.example.domain.service.impl;

import com.example.domain.entity.Role;
import com.example.domain.entity.User;
import com.example.domain.exception.NoContentFoundException;
import com.example.domain.exception.UserNotFound;
import com.example.domain.service.UserService;
import com.example.gateway.repository.UserRepository;
import com.example.gateway.rest.datacontract.RegisterDataContract;
import com.example.gateway.rest.datacontract.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    @Transactional
    public User save(RegisterDataContract register) {
        var user = mapper.map(register, User.class);
        user.setIsHotUser(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setLastLoginTime(LocalDateTime.now());
        user.setRoles(List.of(Role.builder().id(2L).name("USER").build()));
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        return userRepository.save(user);
    }

    @Override
    public List<User> searchByUserName(String username) {
        var users = userRepository.findByUsernameContaining(username);
        if (users.isEmpty()) {
            throw new NoContentFoundException("User not found");
        }
        return users;
    }

    @Override
    @Transactional
    public User updateUser(UUID id, UserDTO requestDTO) {
        log.info("Update user {}", id);
        var userFound = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFound("User " + id + " not found"));
        userFound.setEmail(requestDTO.getEmail());
        userFound.setIsHotUser(requestDTO.getIsHotUser());
        return userRepository.save(userFound);
    }

    @Override
    public User fetchByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NoContentFoundException("User not found"));
    }
}
