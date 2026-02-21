package com.admin.service.repository;

import com.admin.service.entities.Flag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<Flag, Long> {

    Optional<Flag> findById(UUID id);
}
