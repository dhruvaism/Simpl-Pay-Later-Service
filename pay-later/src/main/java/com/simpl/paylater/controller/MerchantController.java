package com.simpl.paylater.controller;

import com.simpl.paylater.dto.MerchantRequestBody;
import com.simpl.paylater.dto.MerchantUpdateBody;
import com.simpl.paylater.dto.Response;
import com.simpl.paylater.dto.UserRequestBody;
import com.simpl.paylater.enitity.Merchant;
import com.simpl.paylater.enitity.User;
import com.simpl.paylater.service.MerchantService;
import com.simpl.paylater.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class MerchantController {
    MerchantService merchantService;

    @Autowired
    public MerchantController(MerchantService merchantService){
        this.merchantService = merchantService;
    }


    /**
     * This method takes a new merchant and add to the database
     * @param merchant's details to be added
     * @return Merchant details
     */
    @PostMapping("/merchant/add")
    public ResponseEntity addMerchant(@RequestBody final MerchantRequestBody merchantRequestBody){
        Merchant merchant = this.merchantService.addMerchant(merchantRequestBody);
        return new ResponseEntity<>(new Response(merchant,"merchant added successfully",true), HttpStatus.CREATED);
    }


    /**
     * This method takes merchant name and new discount percentage and update to the database
     * @param merchant name and discount percentage
     * @return updated Merchant details
     */
    @PutMapping("/merchant/update")
    public ResponseEntity updateDiscountPercentage(@RequestParam("name") String merchantName, @RequestBody final MerchantUpdateBody merchantUpdateBody){
        Merchant merchant = this.merchantService.updateDiscountPercentage(merchantName, merchantUpdateBody);
        return new ResponseEntity<>(new Response(merchant,"discount percentage updated successfully",true), HttpStatus.OK);
    }

    /**
     * This method takes merchant name and return Merchant details
     * @param merchant name
     * @return Merchant details
     */
    @GetMapping("/merchant")
    public ResponseEntity getMerchantByName(@RequestParam("name") String name){
        Merchant merchant = this.merchantService.findMerchantByName(name);
        return new ResponseEntity<>(new Response(merchant,true), HttpStatus.OK);
    }
}
