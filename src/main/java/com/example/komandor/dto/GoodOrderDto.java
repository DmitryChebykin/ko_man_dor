package com.example.komandor.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class GoodOrderDto {
    private String id;

    private Integer count;
}
