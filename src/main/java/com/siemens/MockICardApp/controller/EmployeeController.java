package com.siemens.MockICardApp.controller;

import com.siemens.MockICardApp.data.dto.EmployeeCreateDTO;
import com.siemens.MockICardApp.data.dto.EmployeeReadDTO;
import com.siemens.MockICardApp.data.model.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.siemens.MockICardApp.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public List<EmployeeReadDTO> getAllEmployees() {
        return employeeService.getEmployeeList();
    }

    @GetMapping("/{id}")
    public EmployeeReadDTO getEmployeeById(@PathVariable String id) {
        return employeeService.getEmployeeById(id);
    }

    @PostMapping("/")
    public Employee addEmployee(@RequestBody EmployeeCreateDTO employeeCreateDTO) {
        return employeeService.createEmployee(employeeCreateDTO);
    }

    @PostMapping("/{id}")
    public Employee updateEmployeeById(@PathVariable String id, @RequestBody EmployeeCreateDTO employeeCreateDTO) {
        return employeeService.updateEmployee(id, employeeCreateDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable String id) {
        employeeService.deleteEmployee(id);
    }
}