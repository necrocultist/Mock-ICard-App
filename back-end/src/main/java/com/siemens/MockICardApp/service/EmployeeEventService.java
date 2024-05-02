package com.siemens.MockICardApp.service;

import com.siemens.MockICardApp.data.dto.EmployeeEventWriteDTO;
import com.siemens.MockICardApp.data.dto.EmployeeEventReadDTO;
import com.siemens.MockICardApp.data.dto.converter.DTOConverter;
import com.siemens.MockICardApp.data.enums.Building;
import com.siemens.MockICardApp.data.model.entity.Employee;
import com.siemens.MockICardApp.data.model.entity.EmployeeEvent;
import com.siemens.MockICardApp.data.model.event.DeviceEvent;
import com.siemens.MockICardApp.graphql.inputOutput.EntranceExitInfo;
import com.siemens.MockICardApp.repository.EmployeeEventRepository;
import com.siemens.MockICardApp.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeEventService {
    @Autowired
    private EmployeeEventRepository employeeEventRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeEventReadDTO createEmployeeEvent(String employeeId, EmployeeEventWriteDTO employeeEventWriteDTO) {
        try {
            Employee employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + employeeId));
            EmployeeEvent employeeEvent = DTOConverter.fromEmployeeEventCreateDTOToEmployeeEvent(employee, employeeEventWriteDTO);
            employeeEventRepository.save(employeeEvent);
            return DTOConverter.fromEmployeeEventToEmployeeEventReadDTO(employeeEvent);
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


    public EmployeeEventReadDTO getEmployeeEventByEventId(String id) {
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

    public List<EmployeeEventReadDTO> getEntranceExitEvents(String employeeId, String selectedDay) {
        try {
            String formattedSelectedDay = selectedDay.substring(0, 4) + selectedDay.substring(5, 7) + selectedDay.substring(8, 10);

            List<EmployeeEvent> employeeEvents = employeeEventRepository.findByEmployeeId(employeeId);

            List<EmployeeEvent> filteredEvents = new ArrayList<>();
            for (EmployeeEvent event : employeeEvents) {
                if (event.getEventTime().substring(6, 14).equals(formattedSelectedDay)) {
                    filteredEvents.add(event);
                }
            }

            // Sort the events by eventTime
            filteredEvents.sort(Comparator.comparing(EmployeeEvent::getEventTime));

            if (filteredEvents.isEmpty()) {
                return null;
            }

            if (filteredEvents.size() == 1) {
                return Arrays.asList(
                        DTOConverter.fromEmployeeEventToEmployeeEventReadDTO(filteredEvents.get(0)),
                        null
                );
            }

            return Arrays.asList(
                    DTOConverter.fromEmployeeEventToEmployeeEventReadDTO(filteredEvents.get(0)), // First event (entrance)
                    DTOConverter.fromEmployeeEventToEmployeeEventReadDTO(filteredEvents.get(filteredEvents.size() - 1)) // Last event (exit)
            );
        } catch (Exception e) {
            System.out.println("An error occurred while fetching employee entrance and exit events: " + e.getMessage());
            throw e;
        }
    }

    public void processEmployeeEvent(DeviceEvent deviceEvent) {
        try {
            EmployeeEventWriteDTO employeeEventWriteDTO = new EmployeeEventWriteDTO(Building.valueOf(deviceEvent.getBuilding()), deviceEvent.getEventTime());
            createEmployeeEvent(deviceEvent.getEmployeeId(), employeeEventWriteDTO);
        } catch (Exception e) {
            System.out.println("An error occurred while processing employee event: " + e.getMessage());
            throw e;
        }
    }
}
