package com.example.bilingsoftware.Controller;

import com.example.bilingsoftware.Service.OrderService;
import com.example.bilingsoftware.io.OrderRequest;
import com.example.bilingsoftware.io.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {


    private  final OrderService orderService;


    @PostMapping
    public OrderResponse createOrder(@RequestBody OrderRequest request)
    {
       return orderService.createOrder(request);

    }

    @DeleteMapping("{orderid}")
    public  void  deleteOrder(@PathVariable String orderid)
    {
        orderService.deleteOrder(orderid);

    }


    @GetMapping("/all")
    public List<OrderResponse> getallOrder()
    {
        return  orderService.getLatestOrders();
    }
}
