package com.simpl.paylater.service;

import com.simpl.paylater.dto.UserRequestBody;
import com.simpl.paylater.enitity.User;
import com.simpl.paylater.exception.ApiRequestException;
import com.simpl.paylater.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User addUser(UserRequestBody userRequestBody){
        User user = new User(-1,userRequestBody.getName(), userRequestBody.getEmail(), userRequestBody.getCredit_limit(), 0);
        try {
            return this.userRepository.save(user);
        }catch (Exception e){
            throw new ApiRequestException(e.getCause().getMessage());
        }
    }
}
