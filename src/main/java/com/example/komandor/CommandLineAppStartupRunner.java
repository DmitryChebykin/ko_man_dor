package com.example.komandor;

import com.example.komandor.entity.GoodEntity;
import com.example.komandor.repository.GoodEntityRepository;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class CommandLineAppStartupRunner implements CommandLineRunner {
    private final GoodEntityRepository goodEntityRepository;

    @Override
    public void run(String... args) throws Exception {

        Faker faker = new Faker();

        List<GoodEntity> goodEntityList = IntStream.range(1, 99).mapToObj(e -> GoodEntity.builder()
                .name(faker.commerce().productName() + " " + faker.commerce().promotionCode())
                .description(faker.commerce().material())
                .price(BigDecimal.valueOf(Double.parseDouble(faker.commerce().price(15., 10555.).replace(",", "."))))
                .build()).collect(Collectors.toList());

        goodEntityRepository.saveAll(goodEntityList);
    }
}
