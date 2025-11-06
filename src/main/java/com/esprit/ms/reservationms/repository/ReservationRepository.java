package com.esprit.ms.reservationms.repository;

import com.esprit.ms.reservationms.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByClientEmail(String clientEmail);
    List<Reservation> findByDateReservationBetween(LocalDateTime start, LocalDateTime end);
    List<Reservation> findByStatut(Reservation.StatutReservation statut);
    List<Reservation> findByTableId(Long tableId);
}