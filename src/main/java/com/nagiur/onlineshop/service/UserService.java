package com.nagiur.onlineshop.service;

import com.nagiur.onlineshop.domain.User;
import com.nagiur.onlineshop.dto.LoginDTO;
import com.nagiur.onlineshop.dto.UserDTO;

import java.util.Optional;

public interface UserService {
    void saveUser(UserDTO userDTO);
    boolean isNotUniqueUsername(UserDTO user);

    boolean isNotUniqueEmail(UserDTO user);

    User verifyUser(LoginDTO loginDTO);
}
