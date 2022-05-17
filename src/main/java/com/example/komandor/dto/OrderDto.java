package com.example.komandor.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private List<GoodOrderDto> goodOrderDtoList;

    private BigDecimal payment;
}
