package com.example.komandor.service.impl;

import com.example.komandor.dto.GoodDto;
import com.example.komandor.entity.GoodEntity;
import com.example.komandor.repository.GoodEntityRepository;
import com.example.komandor.service.GoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoodServiceImpl implements GoodService {
    private final GoodEntityRepository goodEntityRepository;

    @Transactional(readOnly = true)
    @Override
    public List<GoodDto> getGoodInfoList() {
        List<GoodEntity> goodEntityList = goodEntityRepository.findAll();
        return goodEntityList.stream()
                .map(this::getGoodDto).collect(Collectors.toList());
    }

    private GoodDto getGoodDto(GoodEntity e) {
        return GoodDto.builder()
                .description(e.getDescription())
                .id(e.getId().toString())
                .name(e.getName())
                .price(e.getPrice().toString())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public List<GoodDto> getGoodInfoListByWordInName(String searchWord) {
        List<GoodEntity> goodEntityList = goodEntityRepository.findByNameContainingIgnoreCase(searchWord).orElse(Collections.emptyList());

        return goodEntityList.stream()
                .map(this::getGoodDto).collect(Collectors.toList());
    }
}
