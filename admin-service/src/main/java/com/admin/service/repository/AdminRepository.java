package com.admin.service.repository;

import com.admin.service.entities.Flag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Flag, Long> {
}
