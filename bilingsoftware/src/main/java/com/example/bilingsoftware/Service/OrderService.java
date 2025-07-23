package com.example.bilingsoftware.Service;

import com.example.bilingsoftware.io.OrderRequest;
import com.example.bilingsoftware.io.OrderResponse;
import com.example.bilingsoftware.io.PaymentVerificationRequest;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;

public interface OrderService {


    Double sumSalesByDate(LocalDate date);

    Long countByOrderDate(LocalDate date);

    List<OrderResponse> findRecentOrders();
    OrderResponse createOrder(OrderRequest request);
    void deleteOrder(String orderId);
    List<OrderResponse> getLatestOrders();

    OrderResponse verifyPayment(PaymentVerificationRequest request);
}
