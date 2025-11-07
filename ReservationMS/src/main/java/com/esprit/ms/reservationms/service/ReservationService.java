// Enhanced ReservationService.java
package com.esprit.ms.reservationms.service;

import com.esprit.ms.reservationms.dto.ReservationEvent;
import com.esprit.ms.reservationms.model.Client;
import com.esprit.ms.reservationms.model.Reservation;
import com.esprit.ms.reservationms.repository.ClientRepository;
import com.esprit.ms.reservationms.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RabbitMQProducer rabbitMQProducer;

    @Transactional
    public Reservation createReservation(Reservation reservation) {
        // Gestion automatique du client
        Optional<Client> existingClient = clientRepository.findByEmail(reservation.getClientEmail());
        Client client;

        if (existingClient.isEmpty()) {
            client = new Client(
                    reservation.getClientNom(),
                    reservation.getClientEmail(),
                    reservation.getClientTelephone()
            );
            client = clientRepository.save(client);
        } else {
            // Mise à jour du nombre de visites
            client = existingClient.get();
            client.setNombreVisites(client.getNombreVisites() + 1);
            client = clientRepository.save(client);
        }

        Reservation savedReservation = reservationRepository.save(reservation);

        // Send RabbitMQ event
        ReservationEvent event = new ReservationEvent("CREATED", savedReservation);
        rabbitMQProducer.sendReservationEvent(event);
        rabbitMQProducer.sendNotification(event);

        return savedReservation;
    }

    @Transactional
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

            Reservation updatedReservation = reservationRepository.save(reservation);

            // Send RabbitMQ event
            ReservationEvent event = new ReservationEvent("UPDATED", updatedReservation);
            rabbitMQProducer.sendReservationEvent(event);

            return updatedReservation;
        }
        return null;
    }

    @Transactional
    public boolean deleteReservation(Long id) {
        if (reservationRepository.existsById(id)) {
            Optional<Reservation> reservation = reservationRepository.findById(id);
            reservationRepository.deleteById(id);

            // Send RabbitMQ event for deletion
            if (reservation.isPresent()) {
                ReservationEvent event = new ReservationEvent("DELETED", reservation.get());
                rabbitMQProducer.sendReservationEvent(event);
            }

            return true;
        }
        return false;
    }

    @Transactional
    public Reservation annulerReservation(Long id) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();
            reservation.setStatut(Reservation.StatutReservation.ANNULEE);
            Reservation cancelledReservation = reservationRepository.save(reservation);

            // Send RabbitMQ event
            ReservationEvent event = new ReservationEvent("CANCELLED", cancelledReservation);
            rabbitMQProducer.sendReservationEvent(event);
            rabbitMQProducer.sendNotification(event);

            return cancelledReservation;
        }
        return null;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
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
}