package com.admin.service.services;

import com.admin.service.entities.Flag;
import com.admin.service.repository.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {
    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Flag createFlag(Flag flag) {
        return adminRepository.save(flag);
    }
    public Flag updateFlag(Flag flag){
       Optional<Flag> existingFlag= adminRepository.findById(flag.getId());
       if(existingFlag.isEmpty()){
           new RuntimeException("Entity not found with id");
       }
       return adminRepository.save(flag);
    }
}
