package com.esprit.microservice.facture.service;

import com.esprit.microservice.facture.model.Dashboard;

import java.util.List;

public interface IDashboardService {
    Dashboard createDashboard(Dashboard dashboard);

    Dashboard getDashboardById(Long id);

    List<Dashboard> getAllDashboards();

    Dashboard updateDashboard(Long id, Dashboard dashboard);

    void deleteDashboard(Long id);
}
