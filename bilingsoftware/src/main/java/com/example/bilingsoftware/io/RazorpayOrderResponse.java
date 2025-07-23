package com.example.bilingsoftware.io;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RazorpayOrderResponse {
    private  String id;
    private  String entity;
    private  Integer amount;
    private  String currency;
    private  String status;
    @CreationTimestamp
    @Column(updatable = false)
    private Date  created_at;
    private String receipt;
}
