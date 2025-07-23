package com.example.bilingsoftware.Service.imp;

import com.example.bilingsoftware.Service.RazorpayService;
import com.example.bilingsoftware.io.OrderResponse;
import com.example.bilingsoftware.io.RazorpayOrderResponse;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import jakarta.transaction.Transactional;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RazorpayServiceimp implements RazorpayService {


    @Value("${razorpay.key.id}")
    private  String razorpayKeyId;
    @Value("${razorpay.key.secret}")
    private  String razorpayKeySecret;

    @Override
    public RazorpayOrderResponse createdOrder(Integer amount, String currency) throws RazorpayException {
        RazorpayClient razorpayClient=new RazorpayClient(razorpayKeyId,razorpayKeySecret);
        JSONObject orderRequest=new JSONObject();
        orderRequest.put("amount", amount*100);
        orderRequest.put("currency",currency);
        orderRequest.put("receipt","order_receiptid"+System.currentTimeMillis());
        orderRequest.put("payment_capture",1);

      Order order= razorpayClient.orders.create(orderRequest);
return  convertToResponse(order);
    }

    private RazorpayOrderResponse convertToResponse(Order order) {

    return     RazorpayOrderResponse.builder()
                .id(order.get("id"))
                .entity(order.get("entity"))
                .amount(order.get("amount"))
                .currency(order.get("currency"))
                .status(order.get("status"))
            .created_at(order.get("created_at"))
                .receipt(order.get("receipt"))
                .build();

    }








}
