package com.simpl.paylater.service;

import com.simpl.paylater.dto.MerchantRequestBody;
import com.simpl.paylater.dto.MerchantUpdateBody;
import com.simpl.paylater.enitity.Merchant;
import com.simpl.paylater.exception.ApiRequestException;
import com.simpl.paylater.repository.MerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MerchantService {

    private MerchantRepository merchantRepository;

    @Autowired
    public MerchantService(MerchantRepository merchantRepository){
        this.merchantRepository = merchantRepository;
    }

    public Merchant addMerchant(MerchantRequestBody merchantRequestBody){
        Merchant merchant = new Merchant(-1,merchantRequestBody.getName(), merchantRequestBody.getDiscount_percentage(),0);
        try{
            return this.merchantRepository.save(merchant);
        }catch (Exception e){
            throw new ApiRequestException(e.getCause().getMessage());
        }
    }

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
