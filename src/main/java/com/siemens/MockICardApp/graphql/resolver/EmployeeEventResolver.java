package com.siemens.MockICardApp.graphql.resolver;

import com.siemens.MockICardApp.data.dto.EmployeeEventReadDTO;
import com.siemens.MockICardApp.graphql.inputOutput.AddEmployeeEventInput;
import com.siemens.MockICardApp.data.dto.EmployeeEventWriteDTO;
import com.siemens.MockICardApp.graphql.inputOutput.EntranceExitInfo;
import com.siemens.MockICardApp.data.enums.Building;
import com.siemens.MockICardApp.data.model.entity.Employee;
import com.siemens.MockICardApp.data.model.entity.EmployeeEvent;
import com.siemens.MockICardApp.repository.EmployeeEventRepository;
import com.siemens.MockICardApp.repository.EmployeeRepository;
import com.siemens.MockICardApp.service.EmployeeEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Controller
public class EmployeeEventResolver {
    private final EmployeeEventService employeeEventService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeEventRepository employeeEventRepository;

    @Autowired
    public EmployeeEventResolver(EmployeeEventService employeeEventService) {
        this.employeeEventService = employeeEventService;
    }

    @MutationMapping
    public EmployeeEventReadDTO addEmployeeEvent(@Argument AddEmployeeEventInput input) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(input.getEmployeeId());
        Employee employee = optionalEmployee.orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + input.getEmployeeId()));
        Building building = Building.valueOf(input.getBuilding());
        String eventTime = input.getEventTime();
        EmployeeEventWriteDTO employeeEventWriteDTO = new EmployeeEventWriteDTO(building, eventTime);
        return employeeEventService.createEmployeeEvent(employee.getId(), employeeEventWriteDTO);
    }

    @QueryMapping
    public EntranceExitInfo getEntranceExitInfo(@Argument String employeeId) {
        try {
            List<EmployeeEvent> events = employeeEventRepository.findByEmployeeId(employeeId);

            if (events.isEmpty()) {
                return null;
            }
            if(events.size() == 1) {
                return new EntranceExitInfo(events.get(0).getEventTime(), null);
            }
            else {
                return new EntranceExitInfo(events.get(0).getEventTime(), events.get(events.size() - 1).getEventTime());
            }
        } catch (Exception e) {
            System.out.println("An error occurred while fetching employee entrance and exit times: " + e.getMessage());
            throw e;
        }
    }
}

