package com.example.bilingsoftware.Repo;

import com.example.bilingsoftware.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemEntityRepo  extends JpaRepository<OrderItemEntity,Long>
{


}
