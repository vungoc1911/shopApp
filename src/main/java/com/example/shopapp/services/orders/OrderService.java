package com.example.shopapp.services.orders;

import com.example.shopapp.dto.OrderDTO;
import com.example.shopapp.exception.DataNotFoundException;
import com.example.shopapp.model.Order;
import com.example.shopapp.model.OrderStatus;
import com.example.shopapp.model.User;
import com.example.shopapp.repositories.OrderRepository;
import com.example.shopapp.repositories.UserRepository;
import com.example.shopapp.response.order.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public Order createOrder(OrderDTO request) throws Exception{
        User user = userRepository.findById(request.getUserId()).orElseThrow(
                () -> new DataNotFoundException("User not found" + request.getUserId()));

        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));

        Order order = modelMapper.map(request, Order.class);
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        LocalDate shippingDate = request.getShippingDate() == null
                ? LocalDate.now() : request.getShippingDate();
        if (shippingDate.isBefore(LocalDate.now())) {
            throw new DataNotFoundException("Shipping Date is before current date");
        }
        order.setActive(true);
        order.setShippingDate(shippingDate);
        return orderRepository.save(order);
    }

    @Override
    public Order getOrder(Long id) {
        return null;
    }

    @Override
    public Order updateOrder(OrderDTO request) {
        return null;
    }

    @Override
    public void deleteOrder(Long id) {

    }


    @Override
    public List<OrderResponse> getAllOrders(Long userId) {
        return List.of();
    }
}
