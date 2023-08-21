package com.nagiur.onlineshop.service;

import com.nagiur.onlineshop.domain.User;
import com.nagiur.onlineshop.dto.LoginDTO;
import com.nagiur.onlineshop.dto.UserDTO;
import com.nagiur.onlineshop.repository.UserRepository;

import java.nio.charset.StandardCharsets;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserServiceImpl implements UserService{
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(UserDTO userDTO){
        String encrypted = encryptPassword(userDTO.getPassword());
        User user = new User();

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(encrypted);
        user.setUsername(userDTO.getUsername());

        userRepository.save(user);
    }

    @Override
    public boolean isNotUniqueUsername(UserDTO user) {
        return userRepository.findByUsername(user.getUsername()).isPresent();
    }

    @Override
    public boolean isNotUniqueEmail(UserDTO user) {
        return userRepository.findByEmail(user.getEmail()).isPresent();
    }

    @Override
    public User verifyUser(LoginDTO loginDTO) {
        User user = null;
        try {
            user = userRepository.findByUsername(loginDTO.getUsername()).orElseThrow(()-> new UserPrincipalNotFoundException(
                    "User not found by " + loginDTO.getUsername()
            ));
        } catch (UserPrincipalNotFoundException e) {
            throw new RuntimeException(e);
        }

        var encrypted = encryptPassword(loginDTO.getPassword());
        if (user.getPassword().equals(encrypted)){
            return user;
        }else {
            try {
                throw new UserPrincipalNotFoundException("Incorrect username password");
            } catch (UserPrincipalNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String encryptPassword(String password) {
        try {
            var digest = MessageDigest.getInstance("SHA-256");
            var bytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(bytes);
        }catch (NoSuchAlgorithmException e){
            throw new RuntimeException("unable to encrypt password", e);
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();

        for(byte b: hash){
            String hex = Integer.toHexString(0xff &b);
            if(hex.length() == 1){
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }


}
