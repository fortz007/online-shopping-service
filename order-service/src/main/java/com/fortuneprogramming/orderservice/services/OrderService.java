package com.fortuneprogramming.orderservice.services;

import com.fortuneprogramming.orderservice.dtos.OrderItemDto;
import com.fortuneprogramming.orderservice.dtos.OrderRequestDto;
import com.fortuneprogramming.orderservice.models.Order;
import com.fortuneprogramming.orderservice.models.OrderItem;
import com.fortuneprogramming.orderservice.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequestDto orderRequestDto){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderItem> orderItems = orderRequestDto.getOrderItemsDtoList()
                .stream()
                .map(this::mapDtoToObject)
                .toList();
        order.setOrderItemsList(orderItems);

        orderRepository.save(order);
    }
    private OrderItem mapDtoToObject(OrderItemDto orderItemDto) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(orderItemDto.getId());
        orderItem.setQuantity(orderItemDto.getQuantity());
        orderItem.setPrice(orderItemDto.getPrice());
        orderItem.setSkuCode(orderItemDto.getSkuCode());
        return orderItem;
    }
}
