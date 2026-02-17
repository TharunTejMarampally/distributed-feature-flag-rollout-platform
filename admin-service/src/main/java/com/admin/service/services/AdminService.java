package com.admin.service.services;

import com.admin.service.entities.Flag;
import com.admin.service.repository.AdminRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Flag createFlag(Flag flag) {
        return adminRepository.save(flag);
    }
}
