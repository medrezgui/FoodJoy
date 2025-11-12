package com.esprit.ms.reservationms.controller;

import com.esprit.ms.reservationms.model.Reservation;
import com.esprit.ms.reservationms.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservations")
@CrossOrigin(origins = "*")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        Optional<Reservation> reservation = reservationService.getReservationById(id);
        return reservation.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Reservation createReservation(@RequestBody Reservation reservation) {
        return reservationService.createReservation(reservation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id,
                                                         @RequestBody Reservation reservationDetails) {
        Reservation updatedReservation = reservationService.updateReservation(id, reservationDetails);
        return updatedReservation != null ? ResponseEntity.ok(updatedReservation)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable Long id) {
        boolean deleted = reservationService.deleteReservation(id);
        return deleted ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/client/{email}")
    public List<Reservation> getReservationsByClient(@PathVariable String email) {
        return reservationService.getReservationsByClientEmail(email);
    }

    @GetMapping("/date/{date}")
    public List<Reservation> getReservationsByDate(@PathVariable String date) {
        LocalDateTime dateTime = LocalDateTime.parse(date);
        return reservationService.getReservationsByDate(dateTime);
    }

    @GetMapping("/statut/{statut}")
    public List<Reservation> getReservationsByStatut(@PathVariable String statut) {
        return reservationService.getReservationsByStatut(Reservation.StatutReservation.valueOf(statut.toUpperCase()));
    }

    @PutMapping("/{id}/annuler")
    public ResponseEntity<Reservation> annulerReservation(@PathVariable Long id) {
        Reservation reservation = reservationService.annulerReservation(id);
        return reservation != null ? ResponseEntity.ok(reservation)
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/table/{tableId}")
    public List<Reservation> getReservationsByTable(@PathVariable Long tableId) {
        return reservationService.getReservationsByTableId(tableId);
    }

    @GetMapping("/table/{tableId}/active")
    public Reservation getActiveReservationByTable(@PathVariable Long tableId) {
        return reservationService.getActiveReservationByTableId(tableId);
    }
}