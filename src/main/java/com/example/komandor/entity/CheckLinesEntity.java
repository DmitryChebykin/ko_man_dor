package com.example.komandor.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "checklines", schema = "cashtest", indexes = {@Index(name = "good_index", columnList = "good_id")})
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckLinesEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "good_id")
    private GoodEntity goodEntity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "check_id")
    private CheckTotalEntity checkTotalEntity;

    @Column(name = "line_number")
    private Integer lineNumber;

    @Column(name = "sum", scale = 2, precision = 9)
    private BigDecimal sum;

    @Column(name = "count")
    private Integer goodCount;
}
