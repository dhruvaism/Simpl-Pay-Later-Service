package com.simpl.paylater.controller;

import com.simpl.paylater.dto.Response;
import com.simpl.paylater.dto.TransactionRequestBody;
import com.simpl.paylater.enitity.Transaction;
import com.simpl.paylater.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    TransactionService transactionService;


    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * This method takes a Transaction details and add it to the Database
     * @param Transaction details
     * @return Tranaction details
     */
    @PostMapping("/transaction/add")
    public ResponseEntity addTransaction(@RequestBody TransactionRequestBody transactionRequestBody){
        Transaction transaction = this.transactionService.addTransaction(transactionRequestBody);
        return new ResponseEntity<>(new Response(transaction,"Success! Transaction made successfully",true), HttpStatus.CREATED);

    }
}
