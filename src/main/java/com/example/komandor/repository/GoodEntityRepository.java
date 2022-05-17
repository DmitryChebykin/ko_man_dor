package com.example.komandor.repository;

import com.example.komandor.entity.GoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GoodEntityRepository extends JpaRepository<GoodEntity, UUID> {
    Optional<List<GoodEntity>> findByNameContainingIgnoreCase(String searchWord);

    Optional<List<GoodEntity>> findByIdIn(List<UUID> setIds);
}