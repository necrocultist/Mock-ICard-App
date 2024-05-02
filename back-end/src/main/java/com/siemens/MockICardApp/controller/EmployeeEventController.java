package com.siemens.MockICardApp.controller;

import com.siemens.MockICardApp.data.dto.EmployeeEventWriteDTO;
import com.siemens.MockICardApp.data.dto.EmployeeEventReadDTO;
import com.siemens.MockICardApp.data.model.entity.EmployeeEvent;
import com.siemens.MockICardApp.service.EmployeeEventService;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
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
        return employeeEventService.getEmployeeEventByEventId(eventId);
    }

    @PostMapping("/")
    public EmployeeEventReadDTO addEmployeeEvent(@PathVariable String employeeId, @RequestBody EmployeeEventWriteDTO employeeEventWriteDTO) {
        return employeeEventService.createEmployeeEvent(employeeId, employeeEventWriteDTO);
    }

    @DeleteMapping("/{eventId}")
    public void deleteEmployeeEvent(@PathVariable String eventId) {
        employeeEventService.deleteEmployeeEvent(eventId);
    }

    @GetMapping("/entrance-exit/{selectedDay}")
    public List<EmployeeEventReadDTO> getEntranceExitEvents(@PathVariable String employeeId, @PathVariable String selectedDay) {
        return employeeEventService.getEntranceExitEvents(employeeId, selectedDay);
    }
}

