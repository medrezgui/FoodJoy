package com.esprit.microservice.employee.rabbit;

import com.esprit.microservice.employee.EntityEmployee.Employee;
import com.esprit.microservice.employee.RepositoryEmployee.EmployeeRepository;
import com.esprit.microservice.employee.dto.ReservationEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationEventConsumer {

    private final EmployeeRepository repository;

    public ReservationEventConsumer(EmployeeRepository repository) {
        this.repository = repository;
    }

    @RabbitListener(queues = "reservation.queue")
    public void handleReservationEvent(ReservationEvent event) {

        System.out.println("📩 Réservation reçue : " + event.getReservationId()
                + " Type=" + event.getEventType());

        // On n’assigne que si eventType == CREATED
        if (!"CREATED".equals(event.getEventType())) {
            return;
        }

        // Trouver un serveur dispo
        List<Employee> employees =
                repository.findByRoleAndAvailableTrueOrderByAssignmentsCountAsc("Serveur");

        if (employees.isEmpty()) {
            System.out.println("❌ Aucun serveur disponible");
            return;
        }

        Employee selected = employees.get(0);

        selected.setAvailable(false);
        selected.setAssignmentsCount(selected.getAssignmentsCount() + 1);

        repository.save(selected);

        System.out.println("✅ Serveur assigné automatiquement : "
                + selected.getFirstName() + " " + selected.getLastName());
    }
}
