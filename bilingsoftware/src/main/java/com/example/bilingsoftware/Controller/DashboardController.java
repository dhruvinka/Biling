package com.example.bilingsoftware.Controller;

import com.example.bilingsoftware.Service.OrderService;
import com.example.bilingsoftware.io.DashbordResponse;
import com.example.bilingsoftware.io.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final OrderService orderService;

    /**
     * GET /dashboard
     * Returns today's sales summary + the 5 most recent orders.
     */
    @GetMapping
    public DashbordResponse getDashboard() {
        LocalDate today = LocalDate.now();

        // 1) today's total sales
        Double sales = orderService.sumSalesByDate(today);
        if (sales == null) {
            sales = 0.0;
        }

        // 2) today's order count
        Long count = orderService.countByOrderDate(today);
        if (count == null) {
            count = 0L;
        }

        // 3) the top‑5 most recent orders
        List<OrderResponse> recents = orderService.findRecentOrders();

        return DashbordResponse.builder()
                .todaysSales(sales)
                .todaysorderCount(count)
                .recentsOrders(recents)
                .build();
    }
}
