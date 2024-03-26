package com.siemens.MockICardApp.graphql.resolver;

import com.siemens.MockICardApp.graphql.inputOutput.AddEmployeeEventInput;
import com.siemens.MockICardApp.data.dto.EmployeeEventCreateDTO;
import com.siemens.MockICardApp.graphql.inputOutput.EntranceExitInfo;
import com.siemens.MockICardApp.data.enums.Building;
import com.siemens.MockICardApp.data.model.entity.Employee;
import com.siemens.MockICardApp.data.model.entity.EmployeeEvent;
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
    public EmployeeEventResolver(EmployeeEventService employeeEventService) {
        this.employeeEventService = employeeEventService;
    }

    @MutationMapping
    public EmployeeEvent addEmployeeEvent(@Argument AddEmployeeEventInput input) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(input.getEmployeeId());
        Employee employee = optionalEmployee.orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + input.getEmployeeId()));
        Building building = Building.valueOf(input.getBuilding());
        Timestamp eventTime = Timestamp.valueOf(input.getEventTime()); // Illegal argument exception
        EmployeeEventCreateDTO employeeEventCreateDTO = new EmployeeEventCreateDTO(building, eventTime);
        return employeeEventService.createEmployeeEvent(employee.getId(), employeeEventCreateDTO);
    }

    @QueryMapping
    public EntranceExitInfo getEntranceExitInfo(@Argument String employeeId) {
        List<EmployeeEvent> entranceExitInfo = employeeEventService.getEmployeeEntranceExitTimes(employeeId);
        if (entranceExitInfo == null || entranceExitInfo.isEmpty()) {
            return null;
        } else if (entranceExitInfo.size() == 1) {
            return new EntranceExitInfo(entranceExitInfo.get(0).getEventTime(), null); // Only entrance event
        }
        return new EntranceExitInfo(entranceExitInfo.get(0).getEventTime(), entranceExitInfo.get(1).getEventTime());
    }
}

