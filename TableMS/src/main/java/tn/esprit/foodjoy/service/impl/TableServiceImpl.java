package tn.esprit.foodjoy.service.impl;

import com.netflix.discovery.converters.Auto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.foodjoy.controller.EmployeeClient;
import tn.esprit.foodjoy.controller.ReservationClient;
import tn.esprit.foodjoy.dto.EmployeeDto;
import tn.esprit.foodjoy.dto.ReservationDto;
import tn.esprit.foodjoy.dto.ReservationEvent;
import tn.esprit.foodjoy.dto.TableDto;
import tn.esprit.foodjoy.entity.Espace;
import tn.esprit.foodjoy.entity.TableResto;
import tn.esprit.foodjoy.entity.TableStatus;
import tn.esprit.foodjoy.repository.EspaceRepository;
import tn.esprit.foodjoy.repository.TableRepository;
import tn.esprit.foodjoy.service.TableService;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
@RequiredArgsConstructor
@Slf4j
public class TableServiceImpl implements TableService {

    private final TableRepository tableRepository;
    private final EspaceRepository espaceRepository;

    //private static final Logger log = LoggerFactory.getLogger(TableService.class);

    //Communication rabbitMQ
    public void receiveReservationStatus(ReservationEvent reservationEvent) {
        log.info("Traitement de la réservation reçue : {}", reservationEvent);

        Long tableId = reservationEvent.getTableId();
        String status = reservationEvent.getStatus();

        try {
            TableResto table = tableRepository.findById(tableId)
                    .orElseThrow(() -> new RuntimeException("Table non trouvée : " + tableId));

            switch (status.toUpperCase()) {
                case "CONFIRMEE" -> table.setStatus(TableStatus.RESERVED);
                case "TERMINEE", "ANNULEE" -> table.setStatus(TableStatus.FREE);
                case "EN_ATTENTE" -> {
                    // Optional: handle pending status
                    // table.setStatus(TableStatus.RESERVED); or keep current
                }
                default -> log.warn("Statut de réservation inconnu : {}", status);
            }

            tableRepository.save(table);
            log.info("Statut de la table {} mis à jour à : {}", tableId, table.getStatus());

        } catch (RuntimeException e) {
            log.error("Erreur lors du traitement de la réservation pour table {}: {}", tableId, e.getMessage());
        }
    }

    public void freeTable(Long tableId) {
        try {
            TableResto table = tableRepository.findById(tableId)
                    .orElseThrow(() -> new RuntimeException("Table non trouvée : " + tableId));

            table.setStatus(TableStatus.FREE);
            tableRepository.save(table);
            log.info("Table {} libérée (réservation supprimée)", tableId);

        } catch (RuntimeException e) {
            log.error("Erreur lors de la libération de la table {}: {}", tableId, e.getMessage());
        }
    }


    private TableResto mapToEntity(TableDto dto) {
        Espace espace = espaceRepository.findById(dto.getEspaceId())
                .orElseThrow(() -> new RuntimeException("Espace not found with ID: " + dto.getEspaceId()));

        return TableResto.builder()
                .tableNumber(dto.getTableNumber())
                .capacity(dto.getCapacity())
                .espace(espace)
                .status(dto.getStatus() != null ? dto.getStatus() : TableStatus.FREE)
                .assignedServerId(dto.getAssignedServerId())
                .positionX(dto.getPositionX())
                .positionY(dto.getPositionY())
                .build();
    }
    @Override
    public TableDto createTable(TableDto dto) {
        TableResto table = mapToEntity(dto);
        table = tableRepository.save(table);
        return mapToDto(table);
    }


    @Override
    public TableDto updateTable(Long id, TableDto dto) {
        TableResto table = tableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Table not found"));
        Espace espace = espaceRepository.findById(dto.getEspaceId())
                .orElseThrow(() -> new RuntimeException("Espace not found with ID: " + dto.getEspaceId()));

        table.setTableNumber(dto.getTableNumber());
        table.setCapacity(dto.getCapacity());
        table.setEspace(espace);
        table.setStatus(dto.getStatus());
        table.setAssignedServerId(dto.getAssignedServerId());
        table.setPositionX(dto.getPositionX());
        table.setPositionY(dto.getPositionY());
        table = tableRepository.save(table);
        return mapToDto(table);
    }

    @Override
    public TableDto getTableById(Long id) {
        return tableRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new RuntimeException("Table not found"));
    }

    @Override
    public List<TableDto> getAllTables() {
        return tableRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTable(Long id) {
        tableRepository.deleteById(id);
    }

    //Employee Service
    @Autowired
    private EmployeeClient employeeServiceClient;

    @Override
    public EmployeeDto getEmployeeById(Long id){
        return employeeServiceClient.getEmployeeById(id);
    }
    @Override
    public List<EmployeeDto> getEmployeesByRole(String roleName){
        return employeeServiceClient.getEmployeesByRole(roleName);
    }

    //Reservation Service
    @Autowired
    private ReservationClient reservationServiceClient;
    @Override
    public List<ReservationEvent> getReservationsByTableId(Long tableId) {
        return reservationServiceClient.getReservationsByTableId(tableId);
    }
    @Override
    public ReservationEvent getActiveReservationByTableId(Long tableId) {
        return reservationServiceClient.getActiveReservationByTableId(tableId);
    }

    @Override
    public List<TableDto> findFreeTables() {
        return tableRepository.findByStatus(TableStatus.FREE).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TableDto> findTablesByEspaceNom(String nom) {
        return tableRepository.findByEspace_Nom(nom).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TableDto> findTablesByStatus(TableStatus status) {
        return tableRepository.findByStatus(status).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TableDto> findAvailableTablesForGroup(Integer groupSize) {
        return tableRepository.findByStatusAndCapacityGreaterThanEqual(TableStatus.FREE, groupSize).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public TableDto assignServerToTable(Long tableId, Long serverId) {
        TableResto table = tableRepository.findById(tableId)
                .orElseThrow(() -> new RuntimeException("Table not found"));
        table.setAssignedServerId(serverId);
        table = tableRepository.save(table);
        return mapToDto(table);
    }

    @Override
    public TableDto updateTableStatus(Long tableId, TableStatus status) {
        TableResto table = tableRepository.findById(tableId)
                .orElseThrow(() -> new RuntimeException("Table not found"));
        table.setStatus(status);
        table = tableRepository.save(table);
        return mapToDto(table);
    }

    // Mappers
    private TableDto mapToDto(TableResto table) {
        return TableDto.builder()
                .id(table.getId())
                .tableNumber(table.getTableNumber())
                .capacity(table.getCapacity())
                .espaceId(table.getEspace().getId())
                .espaceNom(table.getEspace().getNom())  // pour affichage
                .status(table.getStatus())
                .assignedServerId(table.getAssignedServerId())
                .positionX(table.getPositionX())
                .positionY(table.getPositionY())
                .build();
    }




}
