package com.admin.service.controller;

import com.admin.service.entities.Flag;
import com.admin.service.services.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin-service")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    @PostMapping("/create")
    public ResponseEntity<Flag> createFlag(@RequestBody Flag flag) {
        Flag response=adminService.createFlag(flag);
        return ResponseEntity.ok(response);
    }
}
