package com.example.bilingsoftware.Controller;

import com.example.bilingsoftware.Service.OrderService;
import com.example.bilingsoftware.Service.RazorpayService;
import com.example.bilingsoftware.io.OrderResponse;
import com.example.bilingsoftware.io.PaymentRequest;
import com.example.bilingsoftware.io.PaymentVerificationRequest;
import com.example.bilingsoftware.io.RazorpayOrderResponse;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController
{


    private  final RazorpayService razorpayService;
    private  final OrderService orderService;




    @PostMapping
    public RazorpayOrderResponse createdRazorpayOrder(@RequestBody PaymentRequest request) throws RuntimeException, RazorpayException {
      return    razorpayService.createdOrder(request.getAmount(),request.getCurrency());
    }

    @PostMapping("/verify")
    public OrderResponse verifyPayment(@RequestBody PaymentVerificationRequest request)
    {
        return  orderService.verifyPayment(request);
    }


}
