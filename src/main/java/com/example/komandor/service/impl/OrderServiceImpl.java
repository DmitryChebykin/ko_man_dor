package com.example.komandor.service.impl;

import com.example.komandor.dto.GoodOrderDto;
import com.example.komandor.dto.OrderDto;
import com.example.komandor.entity.CheckLinesEntity;
import com.example.komandor.entity.CheckTotalEntity;
import com.example.komandor.entity.GoodEntity;
import com.example.komandor.repository.CheckLinesEntityRepository;
import com.example.komandor.repository.CheckTotalEntityRepository;
import com.example.komandor.repository.GoodEntityRepository;
import com.example.komandor.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final GoodEntityRepository goodEntityRepository;

    private final CheckTotalEntityRepository checkTotalEntityRepository;

    private final CheckLinesEntityRepository checkLinesEntityRepository;

    private final Clock clock;

    @Transactional
    @Override
    public void createCheck(final OrderDto orderDto) {
        Map<String, Integer> collapsedOrder = orderDto.getGoodOrderDtoList()
                .stream()
                .collect(Collectors.groupingBy(GoodOrderDto::getId, Collectors.summingInt(GoodOrderDto::getCount)));

        List<UUID> uuidList = collapsedOrder.keySet().stream().map(UUID::fromString).collect(Collectors.toList());

        List<GoodEntity> goodEntityList = goodEntityRepository.findByIdIn(uuidList).orElseThrow(() -> new RuntimeException("Список товаров не найден в БД"));

        BigDecimal rightOrderSum = goodEntityList.stream()
                .map(e -> e.getPrice().multiply(BigDecimal.valueOf(collapsedOrder.get(e.getId().toString()))))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (rightOrderSum.compareTo(orderDto.getPayment()) != 0) {
            //TODO create dedicated Exception
            throw new RuntimeException("Сумма платежа не соответствует стоимости заказа");
        }

        CheckTotalEntity checkTotalEntity = checkTotalEntityRepository.save(CheckTotalEntity.builder()
                .date(LocalDate.now(clock))
                .time(LocalTime.now(clock))
                .totalSum(rightOrderSum).build());

        Map<String, GoodEntity> goodEntityMap = goodEntityList.stream().collect(Collectors.toMap(e -> e.getId().toString(), Function.identity()));

        AtomicInteger count = new AtomicInteger(0);

        List<CheckLinesEntity> checkLinesEntities = collapsedOrder.keySet().stream().map(e -> CheckLinesEntity.builder()
                .goodEntity(goodEntityMap.get(e))
                .checkTotalEntity(checkTotalEntity)
                .goodCount(collapsedOrder.get(e))
                .sum(goodEntityMap.get(e).getPrice().multiply(BigDecimal.valueOf(collapsedOrder.get(e))))
                .lineNumber(count.incrementAndGet())
                .build()).collect(Collectors.toList());

        checkLinesEntityRepository.saveAll(checkLinesEntities);
    }
}
