package com.abhimanyu.ecomapplication.Repository;

import com.abhimanyu.ecomapplication.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
}
