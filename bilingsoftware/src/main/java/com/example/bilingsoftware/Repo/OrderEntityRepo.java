package com.example.bilingsoftware.Repo;

import com.example.bilingsoftware.entity.OrderEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrderEntityRepo extends JpaRepository<OrderEntity, Long> {

    Optional<OrderEntity> findByOrderId(String orderId);

    List<OrderEntity> findAllByOrderByCreatedAtDesc();

    // sum of grandTotal for a given date
    @Query("""
      SELECT SUM(o.grandTotal)
      FROM OrderEntity o
      WHERE FUNCTION('date', o.createdAt) = :date
    """)
    Double sumSalesByDate(@Param("date") LocalDate date);

    // count of orders for a given date
    @Query("""
      SELECT COUNT(o)
      FROM OrderEntity o
      WHERE FUNCTION('date', o.createdAt) = :date
    """)
    Long countByOrderDate(@Param("date") LocalDate date);

    // recent orders, sorted by createdAt DESC
    @Query("""
      SELECT o
      FROM OrderEntity o
      ORDER BY o.createdAt DESC
    """)

    @Transactional(readOnly = true)
    List<OrderEntity> findRecentOrders(Pageable pageable);
}
