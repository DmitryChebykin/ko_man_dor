package com.example.komandor.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoodDto {
    private String id;

    private String name;

    private String description;

    private String price;
}
