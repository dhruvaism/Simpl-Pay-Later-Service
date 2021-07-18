package com.simpl.paylater.service;

import com.simpl.paylater.dto.MerchantRequestBody;
import com.simpl.paylater.dto.MerchantUpdateBody;
import com.simpl.paylater.enitity.Merchant;
import com.simpl.paylater.enitity.User;
import com.simpl.paylater.exception.ApiRequestException;
import com.simpl.paylater.repository.MerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MerchantService {

    private MerchantRepository merchantRepository;

    @Autowired
    public MerchantService(MerchantRepository merchantRepository){
        this.merchantRepository = merchantRepository;
    }


    /**
     * This method takes a new merchant and add to the database
     * @param MerchantRequestBody
     * @return Merchant
     */
    public Merchant addMerchant(MerchantRequestBody merchantRequestBody){
        Merchant merchant = new Merchant();
        merchant.setName(merchantRequestBody.getName());
        merchant.setDiscount_percentage(merchantRequestBody.getDiscount_percentage());
        merchant.setDiscount(0);
        return saveMerchant(merchant);
    }


    /**
     * This method takes a new merchant and add to the database
     * @param Merchant
     * @return Merchant
     */
    public Merchant saveMerchant(Merchant merchant){
        try{
            return this.merchantRepository.save(merchant);
        }catch (Exception e){
            throw new ApiRequestException(e.getCause().getMessage());
        }
    }


    /**
     * This method takes merchant name and return Merchant details
     * @param merchantName
     * @return Merchant
     */
    public Merchant findMerchantByName(String merchantName){
        try{
            final Optional<Merchant> merchant = this.merchantRepository.findById(merchantName);
            if(merchant.isPresent()){
                return merchant.get();
            }else{
                throw new ApiRequestException("Merchant doesn't exist");
            }
        }catch (Exception e){
            throw new ApiRequestException(e.getMessage());
        }
    }


    /**
     * This method takes merchant name and new discount percentage and update it to the database
     * @param merchantName, merchantUpdateBody
     * @return Merchant
     */
    public  Merchant updateDiscountPercentage(String merchantName, MerchantUpdateBody merchantUpdateBody){
        Merchant merchant = null;
        merchant = this.merchantRepository.findMerchantByName(merchantName);
        if(merchant==null){
            throw new ApiRequestException("Merchant doesn't exist");
        }else{
            try{
                merchant.setDiscount_percentage(merchantUpdateBody.getDiscount_percentage());
                return this.merchantRepository.save(merchant);
            }catch (Exception e){
                throw new ApiRequestException(e.getCause().getMessage());
            }
        }

    }
}
