package tn.esprit.foodjoy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.foodjoy.dto.EspaceDto;
import tn.esprit.foodjoy.dto.TableDto;
import tn.esprit.foodjoy.entity.Espace;
import tn.esprit.foodjoy.service.EspaceService;

import java.util.List;

@RestController
@RequestMapping("/espaces")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class EspaceController {

    private final EspaceService espaceService;

    @PostMapping
    public ResponseEntity<EspaceDto> create(@RequestBody EspaceDto dto) {
        return ResponseEntity.ok(espaceService.createEspace(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EspaceDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(espaceService.getEspaceById(id));
    }

    @GetMapping
    public ResponseEntity<List<EspaceDto>> getAll() {
        return ResponseEntity.ok(espaceService.getAllEspaces());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EspaceDto> update(@PathVariable Long id, @RequestBody EspaceDto dto) {
        return ResponseEntity.ok(espaceService.updateEspace(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        espaceService.deleteEspace(id);
        return ResponseEntity.noContent().build();
    }
}
