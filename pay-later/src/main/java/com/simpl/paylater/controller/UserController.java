package com.simpl.paylater.controller;

import com.simpl.paylater.dto.Response;
import com.simpl.paylater.dto.TotalDues;
import com.simpl.paylater.dto.UserRequestBody;
import com.simpl.paylater.enitity.Merchant;
import com.simpl.paylater.enitity.Transaction;
import com.simpl.paylater.enitity.User;
import com.simpl.paylater.service.TransactionService;
import com.simpl.paylater.service.UserService;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    /**
     * This method takes a new user details and add it to the database
     * @param userRequestBody
     * @return User
     */
    @PostMapping("/user/add")
    public ResponseEntity addUser(@RequestBody final UserRequestBody userRequestBody){
        User user = this.userService.addUser(userRequestBody);
        return new ResponseEntity<>(new Response(user,"user added successfully",true), HttpStatus.CREATED);
    }

    /**
     * This method takes user name and return user details
     * @param name
     * @return User
     */
    @GetMapping("/user")
    public ResponseEntity getUserByName(@RequestParam("name") String name){
        User user = this.userService.findUserByName(name);
        return new ResponseEntity<>(new Response(user,true), HttpStatus.OK);
    }


    /**
     * This method takes User name and payback amount and update it the the database
     * @param name, amount
     * @return User
     */
    @PutMapping("/user/payback")
    public ResponseEntity payDuesAmountByName(@RequestParam("name") String name, @RequestParam("amount") int amount){
        User user = this.userService.payDuesAmountByName(name,amount);
        return new ResponseEntity<>(new Response(user,"Rs "+amount+ " is Paid successfully",true), HttpStatus.OK);
    }

    /**
     * this method returns all users which has exceeded credit limit
     *  @param isAtCreditLimit
     * @return List<User>
     */
    @GetMapping("/users")
    public ResponseEntity findAllUsersByCreditLimit(@RequestParam("credit-limit") boolean isAtCreditLimit){
        List<User> users = this.userService.findAllUsers(isAtCreditLimit);
        return new ResponseEntity<>(new Response(users,true), HttpStatus.OK);
    }

    /**
     * this method return Total dues
     * @return TotalDues
     */
    @GetMapping("/total-dues")
    public ResponseEntity findTotalDues(){
        TotalDues totalDues = this.userService.findTotalDues();
        return new ResponseEntity<>(new Response(totalDues,true), HttpStatus.OK);

    }

}
