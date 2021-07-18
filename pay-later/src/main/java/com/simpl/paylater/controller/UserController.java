package com.simpl.paylater.controller;

import com.simpl.paylater.dto.Response;
import com.simpl.paylater.dto.UserRequestBody;
import com.simpl.paylater.enitity.User;
import com.simpl.paylater.service.UserService;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }


    @PostMapping("/user/add")
    public ResponseEntity addUser(@RequestBody final UserRequestBody userRequestBody){
        User user = this.userService.addUser(userRequestBody);
        return new ResponseEntity<>(new Response(user,"user added successfully",true), HttpStatus.CREATED);
    }
}
