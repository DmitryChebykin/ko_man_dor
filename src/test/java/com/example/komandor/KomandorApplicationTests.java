package com.example.komandor;

import com.example.komandor.dto.GoodDto;
import com.example.komandor.entity.GoodEntity;
import com.example.komandor.repository.GoodEntityRepository;
import com.example.komandor.service.GoodService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class KomandorApplicationTests {
    private static final PostgreSQLContainer postgresqlContainer;

    static {
        postgresqlContainer = (PostgreSQLContainer) (new PostgreSQLContainer("postgres:12")
                .withUsername("testcontainers")
                .withPassword("Testcontain3rs!")
                .withReuse(true));
        postgresqlContainer.start();
    }

    @Autowired
    GoodEntityRepository goodEntityRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    GoodService goodService;

    @DynamicPropertySource
    public static void setDatasourceProperties(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgresqlContainer::getPassword);
        registry.add("spring.datasource.username", postgresqlContainer::getUsername);
    }

    @AfterEach
    void clearDB() {
        jdbcTemplate.execute("TRUNCATE table cashtest.checklines CASCADE");
        jdbcTemplate.execute("TRUNCATE table cashtest.checks CASCADE");
        jdbcTemplate.execute("TRUNCATE table cashtest.goods CASCADE");
    }

    @Test
    void contextLoads() {
    }

    @Test
    void whenSaveGoodInDB_thanDBContainsGood() {
        GoodEntity goodEntity = goodEntityRepository.save(GoodEntity.builder()
                .description("описание")
                .name("название тестового товара")
                .price(BigDecimal.valueOf(15.22))
                .build());

        UUID id = goodEntity.getId();
        assertNotNull(id);

        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM cashtest.goods", Integer.class);
        assertEquals(1, count);
    }

    @Test
    void whenGetGoodDtoInfoList_thanGoodServiceResultOK() {
        goodEntityRepository.saveAll(getDemoGoodDtoList());
        List<GoodDto> goodInfoList = goodService.getGoodInfoList();
        assertNotNull(goodInfoList);
        assertEquals(3, goodInfoList.size());
    }

    @Test
    void whenSearchGoodByWord_thanResultOK() {
        goodEntityRepository.saveAll(getDemoGoodDtoList());
        List<GoodDto> goodInfoList = goodService.getGoodInfoListByWordInName("компьютер");
        assertNotNull(goodInfoList);
        assertEquals(1, goodInfoList.size());
    }

    @Test
    void whenSearchGoodByWordIgnoreCase_thanResultOK() {
        goodEntityRepository.saveAll(getDemoGoodDtoList());
        List<GoodDto> goodInfoList = goodService.getGoodInfoListByWordInName("КомПьютер");
        assertNotNull(goodInfoList);
        assertEquals(1, goodInfoList.size());
    }

    @Test
    void whenSearchGoodByWord_thanEmptyListIfNotGoodWithThisWord() {
        goodEntityRepository.saveAll(getDemoGoodDtoList());
        List<GoodDto> goodInfoList = goodService.getGoodInfoListByWordInName("FFFFFFFFFFFF");
        assertNotNull(goodInfoList);
        assertEquals(0, goodInfoList.size());
    }

    private List<GoodEntity> getDemoGoodDtoList() {
        List<GoodEntity> goodEntityList = new ArrayList<GoodEntity>();

        GoodEntity goodEntity = GoodEntity.builder()
                .description("описание компьютер 1")
                .name("название компьютер тестового товара 1")
                .price(BigDecimal.valueOf(15.22))
                .build();

        goodEntityList.add(goodEntity);

        goodEntity = GoodEntity.builder()
                .description("описание магнитола 2")
                .name("название магнитола тестового товара 2")
                .price(BigDecimal.valueOf(115.22))
                .build();

        goodEntityList.add(goodEntity);

        goodEntity = GoodEntity.builder()
                .description("описание часы 3")
                .name("название часы тестового товара 3")
                .price(BigDecimal.valueOf(165.22))
                .build();

        goodEntityList.add(goodEntity);

        return goodEntityList;
    }
}
