package com.example.bilingsoftware.Service.imp;

import com.example.bilingsoftware.Repo.OrderEntityRepo;
import com.example.bilingsoftware.Repo.OrderItemEntityRepo;
import com.example.bilingsoftware.Service.OrderService;
import com.example.bilingsoftware.entity.OrderEntity;
import com.example.bilingsoftware.entity.OrderItemEntity;
import com.example.bilingsoftware.io.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Orderserviceimp implements OrderService {


    private  final OrderEntityRepo  orderEntityRepo;
    private  final OrderItemEntityRepo orderItemEntityRepo;


    @Override
    public Double sumSalesByDate(LocalDate date) {
        return orderEntityRepo.sumSalesByDate(date);
    }

    @Override
    public Long countByOrderDate(LocalDate date) {
        return orderEntityRepo.countByOrderDate(date);
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    @Override
    public List<OrderResponse> findRecentOrders() {
        var page = orderEntityRepo.findRecentOrders(PageRequest.of(0, 5));
        return page.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse createOrder(OrderRequest request) {
        // compute grandTotal server‑side
        double subtotal = request.getSubtotal();
        double tax      = request.getTax();
        OrderEntity newOrder = OrderEntity.builder()
                .customerName(request.getCustomerName())
                .phoneNumber(request.getPhoneNumber())
                .subtotal(subtotal)
                .tax(tax)
                .grandTotal(subtotal + tax)
                .paymentMethod(PaymentMethod.valueOf(request.getPaymentMethod()))
                .build();

        PaymentDetails pd = new PaymentDetails();
        pd.setStatus(newOrder.getPaymentMethod() == PaymentMethod.CASH
                ? PaymentDetails.PaymentStatus.COMPLETED
                : PaymentDetails.PaymentStatus.PENDING);
        newOrder.setPaymentDetails(pd);

        List<OrderItemEntity> items = request.getCartItems().stream()
                .map(oi -> OrderItemEntity.builder()
                        .itemId(oi.getItemId())
                        .name(oi.getName())
                        .price(oi.getPrice())
                        .q(oi.getQ())
                        .build())
                .collect(Collectors.toList());
        newOrder.setItems(items);

        newOrder = orderEntityRepo.save(newOrder);
        return convertToResponse(newOrder);
    }

    private OrderResponse convertToResponse(OrderEntity newOrder) {

return  OrderResponse.builder()
        .orderId(newOrder.getOrderId())
        .customerName(newOrder.getCustomerName())
        .phoneNumber(newOrder.getPhoneNumber())
        .subtotal(newOrder.getSubtotal())
        .tax(newOrder.getTax())
        .grandTotal(newOrder.getGrandTotal())
        .paymentMethod(newOrder.getPaymentMethod())
        .items(newOrder.getItems().stream()
                .map(this :: convertToItemResponse)
                .collect(Collectors.toList())
        )
        .paymentDetails(newOrder.getPaymentDetails())
        .createdAt(newOrder.getCreatedAt())
        .build();
    }

    private OrderResponse.OrderItemResponse convertToItemResponse(OrderItemEntity orderItemEntity) {

return  OrderResponse.OrderItemResponse.builder()
        .itemId(orderItemEntity.getItemId())
        .name(orderItemEntity.getName())
        .price(orderItemEntity.getPrice())
        .q(orderItemEntity.getQ())
        .build();

    }

    private OrderItemEntity convertToOrderItemEntity(OrderRequest.OrderItemRequest orderItemRequest) {

     return  OrderItemEntity.builder()
              .itemId(orderItemRequest.getItemId())
              .name(orderItemRequest.getName())
              .price(orderItemRequest.getPrice())
              .q(orderItemRequest.getQ())
              .build();
    }

    private OrderEntity convertToOrderEntity(OrderRequest request) {
        double subtotal = request.getSubtotal();
        double tax      = request.getTax();
        double grandtotal= subtotal+ tax;
        System.out.println("the grand totalid :"+grandtotal);
        System.out.println("The sub total is"+subtotal);
        System.out.println("The sub tax is"+tax);
        return OrderEntity.builder()
                .customerName(request.getCustomerName())
                .phoneNumber(request.getPhoneNumber())
                .subtotal(subtotal)
                .tax(tax)
                .grandTotal(grandtotal)    // ← compute it here
                .paymentMethod(PaymentMethod.valueOf(request.getPaymentMethod()))
                .build();
    }


    @Override
    public void deleteOrder(String orderId) {

        OrderEntity existingOrder=orderEntityRepo.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Order can not found"));
        orderEntityRepo.delete(existingOrder);

    }
    @Transactional
    @Override
    public List<OrderResponse> getLatestOrders() {
      return orderEntityRepo.findAllByOrderByCreatedAtDesc()
               .stream()
               .map(this :: convertToResponse)
               .collect(Collectors.toList());
    }

    @Override
    public OrderResponse verifyPayment(PaymentVerificationRequest request) {

        OrderEntity existingOrder= orderEntityRepo.findByOrderId(request.getOrderId())
                .orElseThrow(()->  new RuntimeException("order not found") );
        if(!verifyRazorpaySignature(request.getRazorpayPaymentId(),request.getRazorpayOrderId(),request.getRazorpaySignatureId()))
        {
            throw  new RuntimeException("Payment verification failed");
        }

        PaymentDetails paymentDetails=existingOrder.getPaymentDetails();
        paymentDetails.setRazorpayPaymentId(request.getRazorpayPaymentId());
        paymentDetails.setRazorpayOrderId(request.getRazorpayOrderId());
        paymentDetails.setRazorpaySignature(request.getRazorpaySignatureId());
        paymentDetails.setStatus(PaymentDetails.PaymentStatus.COMPLETED);

        existingOrder=orderEntityRepo.save(existingOrder);


        return convertToResponse(existingOrder);
    }

    private boolean verifyRazorpaySignature(String razorpayPaymentId, String razorpayOrderId, String razorpaySignatureId) {

        return true;

    }


}
