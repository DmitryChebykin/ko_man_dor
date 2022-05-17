package com.example.komandor.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "checks", schema = "cashtest")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckTotalEntity extends BaseEntity {
    private BigDecimal totalSum;

    @Column(name = "date", columnDefinition = "DATE")
    private LocalDate date;

    @Column(name = "time", columnDefinition = "TIME")
    private LocalTime time;
}
