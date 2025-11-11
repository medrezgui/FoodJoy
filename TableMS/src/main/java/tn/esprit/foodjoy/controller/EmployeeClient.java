package tn.esprit.foodjoy.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tn.esprit.foodjoy.dto.EmployeeDto;

import java.util.List;
//TODO check with Service Employee for correct PORT  & NAME (either in eureka or in app props)
@FeignClient(name="Employee",url = "http://localhost:8088")
public interface EmployeeClient {
    //TODO check with Service Employee for correct path
    @RequestMapping("/employee/{id}")
    public EmployeeDto getEmployeeById(@PathVariable Long id);
    //TODO check with Service Employee for correct path
    @RequestMapping("/employee/role/{roleName}")
    public List<EmployeeDto> getEmployeesByRole(@PathVariable String roleName);
}
