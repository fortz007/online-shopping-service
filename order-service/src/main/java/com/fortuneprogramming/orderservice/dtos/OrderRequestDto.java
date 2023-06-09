package com.fortuneprogramming.orderservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class  OrderRequestDto {

    private List<OrderItemDto> orderItemsDtoList;
}
