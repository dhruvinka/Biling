package com.example.bilingsoftware.io;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashbordResponse
{

    private  Double todaysSales;
    private  Long todaysorderCount;
    private List<OrderResponse> recentsOrders;





}
