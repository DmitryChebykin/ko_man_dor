package com.example.komandor.repository;

import com.example.komandor.entity.CheckTotalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CheckTotalEntityRepository extends JpaRepository<CheckTotalEntity, UUID> {
}