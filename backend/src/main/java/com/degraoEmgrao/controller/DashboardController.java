package com.degraoEmgrao.controller;

import com.degraoEmgrao.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> obterDashboard() {
        return ResponseEntity.ok(dashboardService.obterIndicadoresGerais());
    }
}
