package com.esprit.microservice.employee.ControllerEmployee;

import com.esprit.microservice.employee.EntityEmployee.Employee;
import com.esprit.microservice.employee.ServiceEmployee.EmployeeService;
import com.esprit.microservice.employee.dto.AssignRequest;
import com.esprit.microservice.employee.dto.AssignedEmployeeResponse;
import com.esprit.microservice.employee.dto.EmployeeSimpleStatsResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return service.create(employee);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        return service.update(id, employee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        service.delete(id);
    }

    //@PostMapping("/assign")
    //public AssignedEmployeeResponse assignEmployee(@RequestBody AssignRequest request) {
    //    return service.assignEmployeeToReservation(request);
   // }

	@PostMapping("/{employeeId}/assign-reservation/{reservationId}")
	public ResponseEntity<?> assignReservationToEmployee(@PathVariable Long employeeId, @PathVariable Long reservationId) {
		var updated = service.assignReservationToEmployee(employeeId, reservationId);
		if (updated == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucune réservation trouvée avec cet ID.");
		}
		return ResponseEntity.ok(updated);
	}
    @GetMapping("/stats/simple")
    public EmployeeSimpleStatsResponse getSimpleStats() {
        return service.getSimpleStats();
    }


}