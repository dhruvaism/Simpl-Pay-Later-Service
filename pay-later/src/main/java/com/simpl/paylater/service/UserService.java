package com.simpl.paylater.service;

import com.simpl.paylater.dto.Dues;
import com.simpl.paylater.dto.TotalDues;
import com.simpl.paylater.dto.UserRequestBody;
import com.simpl.paylater.enitity.User;
import com.simpl.paylater.exception.ApiRequestException;
import com.simpl.paylater.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    /**
     * This Method takes User details and add it to the Database
     * @param userRequestBody
     * @return user
     */
    public User addUser(UserRequestBody userRequestBody){
        User user = new User();
        user.setName(userRequestBody.getName());
        user.setEmail(userRequestBody.getEmail());
        user.setCredit_limit(userRequestBody.getCredit_limit());
        return saveUser(user);

    }

    // save User to the DB
    public User saveUser(User user){
        try {
            return this.userRepository.save(user);
        }catch (Exception e){
            throw new ApiRequestException(e.getCause().getMessage());
        }
    }

    // find user by name
    public User findUserByName(String name){
        try{
            final Optional<User> user = this.userRepository.findById(name);
            if(user.isPresent()){
                return user.get();
            }else{
                throw new ApiRequestException("User doesn't exist");
            }
        }catch (Exception e){
            throw new ApiRequestException(e.getMessage());
        }
    }

    //update dues amount
    public User payDuesAmountByName(String name, int amount){
        try{
            User user = this.findUserByName(name);
            int updated_dues = Math.max(0,user.getDues()-amount);
            user.setDues(updated_dues);
            return this.userRepository.save(user);
        }catch (Exception e){
            throw new ApiRequestException(e.getMessage());
        }
    }

    //find all users: isAtCreditLimit == true -> return only the users having credit limit exceeded else return all users
    public List<User> findAllUsers(boolean isAtCreditLimit){
          try{
              if(isAtCreditLimit==true){
                  return this.userRepository.findAllByDuesGreaterThanEqualToCreditLimit();
              }else{
                  return this.userRepository.findAll();
              }
          }catch (Exception e){
              throw new ApiRequestException(e.getMessage());
          }
    }

    // find total dues
    public TotalDues findTotalDues(){
        try{
            List<User> users =  this.findAllUsers(false);
            TotalDues totalDues = new TotalDues();
            totalDues.setTotal_dues(0);
            totalDues.setDues_list(new ArrayList<>());
            for(User user: users){
                totalDues.setTotal_dues(totalDues.getTotal_dues() + user.getDues());
                totalDues.getDues_list().add(new Dues(user.getName(),user.getDues()));
            }
            return totalDues;
        }catch (Exception e){
            throw new ApiRequestException(e.getMessage());
        }
    }



}
