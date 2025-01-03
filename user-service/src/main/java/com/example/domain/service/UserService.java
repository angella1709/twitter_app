package com.example.domain.service;

import com.example.domain.entity.User;
import com.example.gateway.rest.datacontract.RegisterDataContract;
import com.example.gateway.rest.datacontract.UserDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {

    User save(RegisterDataContract register);

    List<User> searchByUserName(String username);

    User updateUser(UUID id, UserDTO requestDTO);

    User fetchByUsername(String username);
}
