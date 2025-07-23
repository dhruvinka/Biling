package com.example.bilingsoftware.Service;

import com.example.bilingsoftware.io.RazorpayOrderResponse;
import com.razorpay.RazorpayException;
import org.springframework.stereotype.Service;


public interface RazorpayService {

    RazorpayOrderResponse createdOrder(Integer amount,String currency) throws RazorpayException;

}
