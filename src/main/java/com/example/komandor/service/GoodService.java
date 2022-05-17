package com.example.komandor.service;

import com.example.komandor.dto.GoodDto;

import java.util.List;

public interface GoodService {
    List<GoodDto> getGoodInfoList();

    List<GoodDto> getGoodInfoListByWordInName(String searchWord);
}
