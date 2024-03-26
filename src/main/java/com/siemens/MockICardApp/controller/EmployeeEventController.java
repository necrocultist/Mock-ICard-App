package com.siemens.MockICardApp.controller;

import com.siemens.MockICardApp.data.dto.EmployeeEventCreateDTO;
import com.siemens.MockICardApp.data.dto.EmployeeEventReadDTO;
import com.siemens.MockICardApp.data.model.entity.EmployeeEvent;
import com.siemens.MockICardApp.service.EmployeeEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee/{employeeId}/events")
public class EmployeeEventController {
    private final EmployeeEventService employeeEventService;

    @Autowired
    public EmployeeEventController(EmployeeEventService employeeEventService) {
        this.employeeEventService = employeeEventService;
    }

    @GetMapping("/")
    public List<EmployeeEventReadDTO> getAllEmployeeEvents(@PathVariable String employeeId) {
        return employeeEventService.getEmployeeEventsByEmployeeId(employeeId);
    }

    @GetMapping("/{eventId}")
    public EmployeeEventReadDTO getEmployeeEventById( @PathVariable String eventId) {
        return employeeEventService.getEmployeeEventById(eventId);
    }

    @PostMapping("/")
    public EmployeeEvent addEmployeeEvent(@PathVariable String employeeId, @RequestBody EmployeeEventCreateDTO employeeEventCreateDTO) {
        return employeeEventService.createEmployeeEvent(employeeId, employeeEventCreateDTO);
    }

    @DeleteMapping("/{eventId}")
    public void deleteEmployeeEvent(@PathVariable String eventId) {
        employeeEventService.deleteEmployeeEvent(eventId);
    }
}

