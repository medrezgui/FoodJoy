package com.esprit.microservice.facture.controller;



import com.esprit.microservice.facture.model.Dashboard;
import com.esprit.microservice.facture.service.IDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboards")
public class DashboardController {

    @Autowired
    private IDashboardService dashboardService;

    @PostMapping
    public ResponseEntity<Dashboard> createDashboard(@RequestBody Dashboard dashboard) {
        return ResponseEntity.ok(dashboardService.createDashboard(dashboard));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dashboard> getDashboardById(@PathVariable Long id) {
        return ResponseEntity.ok(dashboardService.getDashboardById(id));
    }

    @GetMapping
    public ResponseEntity<List<Dashboard>> getAllDashboards() {
        return ResponseEntity.ok(dashboardService.getAllDashboards());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dashboard> updateDashboard(@PathVariable Long id, @RequestBody Dashboard dashboard) {
        return ResponseEntity.ok(dashboardService.updateDashboard(id, dashboard));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDashboard(@PathVariable Long id) {
        dashboardService.deleteDashboard(id);
        return ResponseEntity.noContent().build();
    }
}