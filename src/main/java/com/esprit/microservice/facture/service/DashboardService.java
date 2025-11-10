package com.esprit.microservice.facture.service;

import com.esprit.microservice.facture.model.Dashboard;
import com.esprit.microservice.facture.repository.DashboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DashboardService implements IDashboardService{

    @Autowired
    private DashboardRepository dashboardRepository;

    @Override
    public Dashboard createDashboard(Dashboard dashboard) {
        return dashboardRepository.save(dashboard);
    }

    @Override
    public Dashboard getDashboardById(Long id) {
        return dashboardRepository.findById(id).orElseThrow(() -> new RuntimeException("Dashboard not found"));
    }

    @Override
    public List<Dashboard> getAllDashboards() {
        return dashboardRepository.findAll();
    }

    @Override
    public Dashboard updateDashboard(Long id, Dashboard dashboard) {
        Dashboard existing = getDashboardById(id);
        existing.setDate(dashboard.getDate());
        existing.setChiffreAffaire(dashboard.getChiffreAffaire());
        existing.setNombreCommandes(dashboard.getNombreCommandes());
        existing.setPlatPlusVendu(dashboard.getPlatPlusVendu());
        return dashboardRepository.save(existing);
    }

    @Override
    public void deleteDashboard(Long id) {
        dashboardRepository.deleteById(id);
    }
}
