package com.simpl.paylater.service;


import com.simpl.paylater.dto.TransactionRequestBody;
import com.simpl.paylater.enitity.Merchant;
import com.simpl.paylater.enitity.Transaction;
import com.simpl.paylater.enitity.User;
import com.simpl.paylater.exception.ApiRequestException;
import com.simpl.paylater.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TransactionService {

    TransactionRepository transactionRepository;
    MerchantService merchantService;
    UserService userService;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, MerchantService merchantService, UserService userService){
        this.transactionRepository = transactionRepository;
        this.merchantService = merchantService;
        this.userService = userService;
    }


    /**
     * This method takes a new Transaction and add it to the database
     * @param transactionRequestBody
     * @return Transaction
     */
    @Transactional
    public Transaction addTransaction(TransactionRequestBody transactionRequestBody){
           User user = userService.findUserByName(transactionRequestBody.getUser_name());
           Merchant merchant = merchantService.findMerchantByName(transactionRequestBody.getMerchant_name());
           if(isCreditLimitExceeded(transactionRequestBody.getAmount(),user.getDues(),user.getCredit_limit())){
               throw new ApiRequestException("Rejected! Credit limit exceeded!");
           }
           Transaction transaction = new Transaction();
           transaction.setAmount(transactionRequestBody.getAmount());
           transaction.setMerchant_name(merchant.getName());
           transaction.setUser_name(user.getName());
           transaction = saveTransaction(transaction);

           user.getTransactions().add(transaction);
           user.setDues(user.getDues() + transaction.getAmount());
           merchant.getTransactions().add(transaction);
           merchant.setDiscount(merchant.getDiscount() + calculateDiscount(transaction.getAmount(), merchant.getDiscount_percentage()));

           userService.saveUser(user);
           merchantService.saveMerchant(merchant);

           return transaction;
    }


    /**
     * This method add Transaction detials to the Database
     * @param transaction
     * @return transaction
     */
    public Transaction saveTransaction(Transaction transaction){
        try {
            return this.transactionRepository.save(transaction);
        }catch (Exception e){
            throw new ApiRequestException(e.getCause().getMessage());
        }
    }


    // calculate discount from given amount and discount rate
    public double calculateDiscount(double amount, double discount_percentage){
        return (amount) * (discount_percentage / 100.0);
    }

    // check if credit limit is exceeding
    public boolean isCreditLimitExceeded(int amount, int dues, int creditLimit){
        if(amount + dues > creditLimit) return true;
        return false;
    }
}
