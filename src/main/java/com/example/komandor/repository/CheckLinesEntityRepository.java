package com.example.komandor.repository;

import com.example.komandor.entity.CheckLinesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CheckLinesEntityRepository extends JpaRepository<CheckLinesEntity, UUID> {
}