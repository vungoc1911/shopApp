package com.example.shopapp.controller;

import com.example.shopapp.dto.OrderDTO;
import com.example.shopapp.model.Order;
import com.example.shopapp.response.ResponseObject;
import com.example.shopapp.response.order.OrderResponse;
import com.example.shopapp.services.orders.IOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService orderService;

    @PostMapping("")
    public ResponseEntity<ResponseObject> createOrder(@Valid @RequestBody OrderDTO orderDTO) throws Exception {

        Order orderResponse = orderService.createOrder(orderDTO);
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Insert order successfully")
                .data(orderResponse)
                .status(HttpStatus.OK)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getOrder(@Valid @PathVariable("id") Long orderId) {
        Order existingOrder = orderService.getOrder(orderId);
        return ResponseEntity.ok(new ResponseObject(
                "Get order successfully",
                HttpStatus.OK,
                existingOrder
        ));
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<ResponseObject> getOrders(@Valid @PathVariable("user_id") Long userId) {
        List<OrderResponse> orderResponses = orderService.getAllOrders(userId);
        return ResponseEntity.ok(ResponseObject
                .builder()
                .message("Get list of orders successfully")
                .data(orderResponses)
                .status(HttpStatus.OK)
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateOrder(
            @Valid @RequestBody OrderDTO orderDTO) throws Exception {

        Order order = orderService.updateOrder(orderDTO);
        return ResponseEntity.ok(new ResponseObject("Update order successfully", HttpStatus.OK, order));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteOrder(@Valid @PathVariable Long id) {
        //xóa mềm => cập nhật trường active = false
        orderService.deleteOrder(id);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("order.delete_order.delete_successfully")
                        .build()
        );
    }
}
