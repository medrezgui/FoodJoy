package tn.esprit.foodjoy.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.foodjoy.dto.EspaceDto;
import tn.esprit.foodjoy.entity.Espace;
import tn.esprit.foodjoy.repository.EspaceRepository;
import tn.esprit.foodjoy.service.EspaceService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EspaceServiceImpl implements EspaceService {

    private final EspaceRepository espaceRepository;


    private Espace mapToEntity(EspaceDto dto) {
        return Espace.builder()
                .nom(dto.getNom())
                .description(dto.getDescription())
                .capaciteTotale(dto.getCapaciteTotale())
                .build();
    }

    private EspaceDto mapToDto(Espace espace) {
        return EspaceDto.builder()
                .id(espace.getId())
                .nom(espace.getNom())
                .description(espace.getDescription())
                .capaciteTotale(espace.getCapaciteTotale())
                .build();
    }

    @Override
    public EspaceDto createEspace(EspaceDto dto) {
        Espace espace = mapToEntity(dto);
        espace = espaceRepository.save(espace);
        return mapToDto(espace);
    }

    @Override
    public EspaceDto getEspaceById(Long id) {
        return espaceRepository.findById(id).map(this::mapToDto)
                .orElseThrow(() -> new RuntimeException("Espace not found"));
    }

    @Override
    public List<EspaceDto> getAllEspaces() {
        return espaceRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public EspaceDto updateEspace(Long id, EspaceDto dto) {
        Espace espace = espaceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Espace not found with ID: " + id));
        espace.setNom(dto.getNom());
        espace.setDescription(dto.getDescription());
        espace.setCapaciteTotale(dto.getCapaciteTotale());
        espace = espaceRepository.save(espace);
        return mapToDto(espace);
    }

    @Override
    public void deleteEspace(Long id) {
        espaceRepository.deleteById(id);
    }

}
