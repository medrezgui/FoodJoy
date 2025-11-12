package tn.esprit.foodjoy.service;

import tn.esprit.foodjoy.dto.EspaceDto;

import java.util.List;

public interface EspaceService {

    EspaceDto createEspace(EspaceDto dto);
    EspaceDto getEspaceById(Long id);
    List<EspaceDto> getAllEspaces();
    EspaceDto updateEspace(Long id, EspaceDto dto);
    void deleteEspace(Long id);
}
