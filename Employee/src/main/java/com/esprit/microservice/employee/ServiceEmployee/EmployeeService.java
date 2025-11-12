package com.esprit.microservice.employee.ServiceEmployee;

import com.esprit.microservice.employee.EntityEmployee.Employee;
import com.esprit.microservice.employee.RepositoryEmployee.EmployeeRepository;
import com.esprit.microservice.employee.dto.AssignRequest;
import com.esprit.microservice.employee.dto.AssignedEmployeeResponse;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;
	@Value("${reservation.service.base-url:http://localhost:8089}")
	private String reservationServiceBaseUrl;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<Employee> getAll() {
        return repository.findAll();
    }

    public Employee getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Employee create(Employee employee) {
        return repository.save(employee);
    }

    public Employee update(Long id, Employee employee) {
        Employee existing = repository.findById(id).orElse(null);
        if (existing != null) {
            existing.setFirstName(employee.getFirstName());
            existing.setLastName(employee.getLastName());
            existing.setEmail(employee.getEmail());
            existing.setSalary(employee.getSalary());
            return repository.save(existing);
        }
        return null;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
    public AssignedEmployeeResponse assignEmployeeToReservation(AssignRequest request) {

        // 1 — Trouver les employés du rôle + disponibles
        List<Employee> candidats = repository.findByRoleAndAvailableTrueOrderByAssignmentsCountAsc(
                request.getRole()
        );

        if (candidats.isEmpty()) {
            throw new RuntimeException("Aucun employé disponible pour le rôle : " + request.getRole());
        }

        // 2 — Choisir le moins chargé
        Employee choisi = candidats.get(0);

        // 3 — Mettre à jour l’état de l’employé
        choisi.setAvailable(false);
        choisi.setAssignmentsCount(choisi.getAssignmentsCount() + 1);

        repository.save(choisi);

        // 4 — Retourner DTO
        return new AssignedEmployeeResponse(
                choisi.getId(),
                choisi.getFirstName(),
                choisi.getLastName(),
                choisi.getRole()
        );
    }

	public Employee assignReservationToEmployee(Long employeeId, Long reservationId) {
		Employee employee = repository.findById(employeeId).orElse(null);
		if (employee == null) {
			return null;
		}

		RestTemplate restTemplate = new RestTemplate();
		String url = reservationServiceBaseUrl + "/reservations/" + reservationId;
		try {
			ResponseEntity<ReservationDto> response = restTemplate.getForEntity(url, ReservationDto.class);
			if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
				ReservationDto reservation = response.getBody();
				return repository.save(employee);
			}
			return null;
		} catch (HttpClientErrorException.NotFound ex) {
			return null;
		}
	}

	// Minimal DTO for ReservationMS response mapping
	private static class ReservationDto {
		private Long id;
		private String clientNom;
		public Long getId() { return id; }
		public void setId(Long id) { this.id = id; }
		public String getClientNom() { return clientNom; }
		public void setClientNom(String clientNom) { this.clientNom = clientNom; }
	}
}
