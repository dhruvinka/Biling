package com.example.bilingsoftware.entity;


import com.example.bilingsoftware.io.PaymentDetails;
import com.example.bilingsoftware.io.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_order_items")
public class OrderEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private  String orderId;
    private String phoneNumber;
    private String  customerName;
    private Double subtotal;
    private Double tax;
    private Double grandTotal;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_entity_id") // Foreign key in OrderItemEntity table
    private List<OrderItemEntity> items = new ArrayList<>();


    @Embedded
    private PaymentDetails paymentDetails;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;



    @PrePersist
    protected void onCreate() {
        this.orderId = "ORD-" + java.util.UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
    }


}
