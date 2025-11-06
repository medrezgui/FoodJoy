package com.esprit.ms.reservationms.service;

import com.esprit.ms.reservationms.model.Client;
import com.esprit.ms.reservationms.model.Reservation;
import com.esprit.ms.reservationms.repository.ClientRepository;
import com.esprit.ms.reservationms.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ClientRepository clientRepository;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    public Reservation createReservation(Reservation reservation) {
        // Gestion automatique du client
        Optional<Client> existingClient = clientRepository.findByEmail(reservation.getClientEmail());
        if (existingClient.isEmpty()) {
            Client newClient = new Client(
                    reservation.getClientNom(),
                    reservation.getClientEmail(),
                    reservation.getClientTelephone()
            );
            clientRepository.save(newClient);
        } else {
            // Mise à jour du nombre de visites
            Client client = existingClient.get();
            client.setNombreVisites(client.getNombreVisites() + 1);
            clientRepository.save(client);
        }

        return reservationRepository.save(reservation);
    }

    public Reservation updateReservation(Long id, Reservation reservationDetails) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();
            reservation.setClientNom(reservationDetails.getClientNom());
            reservation.setClientEmail(reservationDetails.getClientEmail());
            reservation.setClientTelephone(reservationDetails.getClientTelephone());
            reservation.setDateReservation(reservationDetails.getDateReservation());
            reservation.setNombrePersonnes(reservationDetails.getNombrePersonnes());
            reservation.setStatut(reservationDetails.getStatut());
            reservation.setCommentaires(reservationDetails.getCommentaires());
            reservation.setTableId(reservationDetails.getTableId());

            return reservationRepository.save(reservation);
        }
        return null;
    }

    public boolean deleteReservation(Long id) {
        if (reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Reservation> getReservationsByClientEmail(String email) {
        return reservationRepository.findByClientEmail(email);
    }

    public List<Reservation> getReservationsByDate(LocalDateTime date) {
        LocalDateTime startOfDay = date.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = date.toLocalDate().atTime(23, 59, 59);
        return reservationRepository.findByDateReservationBetween(startOfDay, endOfDay);
    }

    public List<Reservation> getReservationsByStatut(Reservation.StatutReservation statut) {
        return reservationRepository.findByStatut(statut);
    }

    public Reservation annulerReservation(Long id) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();
            reservation.setStatut(Reservation.StatutReservation.ANNULEE);
            return reservationRepository.save(reservation);
        }
        return null;
    }
}