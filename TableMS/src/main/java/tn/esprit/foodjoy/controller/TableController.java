// tn.esprit.foodjoy.controller.TableController
package tn.esprit.foodjoy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.foodjoy.dto.EmployeeDto;
import tn.esprit.foodjoy.dto.EspaceDto;
import tn.esprit.foodjoy.dto.ReservationEvent;
import tn.esprit.foodjoy.dto.TableDto;
import tn.esprit.foodjoy.entity.TableStatus;
import tn.esprit.foodjoy.service.TableService;

import java.util.List;

@RestController
@RequestMapping("/tables")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class TableController {

    private final TableService tableService;

    @PostMapping
    public ResponseEntity<TableDto> create(@RequestBody TableDto dto) {
        return ResponseEntity.ok(tableService.createTable(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TableDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(tableService.getTableById(id));
    }

    @GetMapping
    public ResponseEntity<List<TableDto>> getAll() {
        return ResponseEntity.ok(tableService.getAllTables());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TableDto> update(@PathVariable Long id, @RequestBody TableDto dto) {
        return ResponseEntity.ok(tableService.updateTable(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tableService.deleteTable(id);
        return ResponseEntity.noContent().build();
    }

    // Fonctions avancées
    @GetMapping("/free")
    public ResponseEntity<List<TableDto>> getFreeTables() {
        return ResponseEntity.ok(tableService.findFreeTables());
    }

    @GetMapping("/espace/nom/{nom}")
    public ResponseEntity<List<TableDto>> getByEspaceNom(@PathVariable String nom) {
        return ResponseEntity.ok(tableService.findTablesByEspaceNom(nom));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TableDto>> getByStatus(@PathVariable TableStatus status) {
        return ResponseEntity.ok(tableService.findTablesByStatus(status));
    }

    @GetMapping("/available-for-group")
    public ResponseEntity<List<TableDto>> getAvailableForGroup(@RequestParam Integer groupSize) {
        return ResponseEntity.ok(tableService.findAvailableTablesForGroup(groupSize));
    }

    @PatchMapping("/{id}/assign-server")
    public ResponseEntity<TableDto> assignServer(@PathVariable Long id, @RequestParam Long serverId) {
        return ResponseEntity.ok(tableService.assignServerToTable(id, serverId));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TableDto> updateStatus(@PathVariable Long id, @RequestParam TableStatus status) {
        return ResponseEntity.ok(tableService.updateTableStatus(id, status));
    }

    // Reservations
    //TODO check with Service Reservations for correct path
    @GetMapping("/reservations/table/{tableId}")
    public List<ReservationEvent> getReservationsByTableId(@PathVariable Long tableId) {
        return tableService.getReservationsByTableId(tableId);
    }
    //TODO check with Service Reservations for correct path
    @GetMapping("/reservations/table/{tableId}/active")
    public ReservationEvent getActiveReservationByTableId(@PathVariable Long tableId) {
        return tableService.getActiveReservationByTableId(tableId);
    }

    //Employee
    //TODO check with Service Employee for correct path
    @GetMapping("/api/employees/{id}")
    public EmployeeDto getEmployeeById(@PathVariable Long id){
        return tableService.getEmployeeById(id);
    }
    //TODO check with Service Employee for correct path
    @GetMapping("/employees/role/{roleName}")
    public List<EmployeeDto> getEmployeesByRole(@PathVariable String roleName){
        return tableService.getEmployeesByRole(roleName);
    }

}