package com.example.shopapp.services.orders;

import com.example.shopapp.dto.OrderDTO;
import com.example.shopapp.model.Order;
import com.example.shopapp.response.order.OrderResponse;

import java.util.List;

public interface IOrderService {

    Order createOrder(OrderDTO request) throws Exception;

    Order getOrder(Long id);

    Order updateOrder(OrderDTO request);

    void deleteOrder(Long id);

    List<OrderResponse> getAllOrders(Long userId);
}
