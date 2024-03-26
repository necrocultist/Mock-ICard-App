package com.siemens.MockICardApp.service;

import com.siemens.MockICardApp.data.dto.EmployeeEventCreateDTO;
import com.siemens.MockICardApp.data.dto.EmployeeEventReadDTO;
import com.siemens.MockICardApp.data.dto.converter.DTOConverter;
import com.siemens.MockICardApp.data.enums.Building;
import com.siemens.MockICardApp.data.model.entity.Employee;
import com.siemens.MockICardApp.data.model.entity.EmployeeEvent;
import com.siemens.MockICardApp.data.model.event.DeviceEvent;
import com.siemens.MockICardApp.repository.EmployeeEventRepository;
import com.siemens.MockICardApp.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeEventService {
    @Autowired
    private EmployeeEventRepository employeeEventRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeEvent createEmployeeEvent(String employeeId, EmployeeEventCreateDTO employeeEventCreateDTO) {
        try {
            Employee employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + employeeId));
            EmployeeEvent employeeEvent = DTOConverter.fromEmployeeEventCreateDTOToEmployeeEvent(employee, employeeEventCreateDTO);
            return employeeEventRepository.save(employeeEvent);
        } catch (Exception e) {
            System.out.println("An error occurred during employee event creation: " + e.getMessage());
            throw e;
        }
    }


    public void deleteEmployeeEvent(String id) {
        try {
            if (employeeEventRepository.existsById(id)) {
                employeeEventRepository.deleteById(id);
            }
        } catch (Exception e) {
            System.out.println("An error occurred during employee event deletion: " + e.getMessage());
            throw e;
        }
    }

    public List<EmployeeEventReadDTO> getEmployeeEventsByEmployeeId(String employeeId) {
        try {
            List<EmployeeEvent> employeeEvents = employeeEventRepository.findByEmployeeId(employeeId);
            return employeeEvents.stream()
                    .map(DTOConverter::fromEmployeeEventToEmployeeEventReadDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println("An error occurred while fetching employee events by employee ID: " + e.getMessage());
            throw e;
        }
    }


    public EmployeeEventReadDTO getEmployeeEventById(String id) {
        try {
            EmployeeEvent employeeEvent = employeeEventRepository.findById(id).orElse(null);
            if (employeeEvent == null) {
                throw new EntityNotFoundException("Employee event with ID " + id + " not found");
            }
            return DTOConverter.fromEmployeeEventToEmployeeEventReadDTO(employeeEvent);
        } catch (EntityNotFoundException e) {
            System.out.println("An error occurred during employee event retrieval: " + e.getMessage());
            throw e;
        }
    }

    public List<EmployeeEvent> getEmployeeEntranceExitTimes(String employeeId) {
        try {
            List<EmployeeEvent> events = employeeEventRepository.findByEmployeeId(employeeId);

            if (events.isEmpty()) {
                return null;
            }

            EmployeeEvent entranceEvent = events.get(0);
            EmployeeEvent exitEvent = events.get(events.size() - 1);

            return List.of(entranceEvent, exitEvent);
        } catch (Exception e) {
            System.out.println("An error occurred while fetching employee entrance and exit times: " + e.getMessage());
            throw e;
        }
    }

    public void processEmployeeEvent(DeviceEvent deviceEvent) {
        try {
            EmployeeEventCreateDTO employeeEventCreateDTO = new EmployeeEventCreateDTO(Building.valueOf(deviceEvent.getBuilding()), Timestamp.valueOf(deviceEvent.getEventTime()));
            createEmployeeEvent(deviceEvent.getEmployeeId(), employeeEventCreateDTO);
        } catch (Exception e) {
            System.out.println("An error occurred while processing employee event: " + e.getMessage());
            throw e;
        }
    }

}