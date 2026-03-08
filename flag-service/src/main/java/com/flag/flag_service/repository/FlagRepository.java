package com.flag.flag_service.repository;

import com.flag.flag_service.entity.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FlagRepository extends JpaRepository<Feature, UUID> {
}
