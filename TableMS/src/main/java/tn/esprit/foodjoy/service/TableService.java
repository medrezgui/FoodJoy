// tn.esprit.foodjoy.service.TableService
package tn.esprit.foodjoy.service;

import org.springframework.web.bind.annotation.PathVariable;
import tn.esprit.foodjoy.dto.*;
import tn.esprit.foodjoy.entity.TableStatus;
import java.util.List;

public interface TableService {

    //rabbitMQ coms
    public void receiveReservationStatus(ReservationEvent reservationEvent);


    TableDto createTable(TableDto tableDto);
    TableDto updateTable(Long id, TableDto tableDto);
    TableDto getTableById(Long id);
    List<TableDto> getAllTables();
    void deleteTable(Long id);
    //Employee Service
    EmployeeDto getEmployeeById(Long id);
    List<EmployeeDto> getEmployeesByRole(String roleName);
    //Reservation Service
    List<ReservationEvent> getReservationsByTableId(Long tableId);
    ReservationEvent getActiveReservationByTableId(Long tableId);
    void freeTable(Long tableId);
    // Fonctions avancées
    List<TableDto> findFreeTables();
    List<TableDto> findTablesByEspaceNom(String nom);
    List<TableDto> findTablesByStatus(TableStatus status);
    List<TableDto> findAvailableTablesForGroup(Integer groupSize); // libres + capacité >= groupSize
    TableDto assignServerToTable(Long tableId, Long serverId);
    TableDto updateTableStatus(Long tableId, TableStatus status);


}