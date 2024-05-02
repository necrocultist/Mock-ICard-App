package com.siemens.MockICardApp.data.dto.converter;

import com.siemens.MockICardApp.data.dto.EmployeeWriteDTO;
import com.siemens.MockICardApp.data.dto.EmployeeEventWriteDTO;
import com.siemens.MockICardApp.data.dto.EmployeeEventReadDTO;
import com.siemens.MockICardApp.data.dto.EmployeeReadDTO;
import com.siemens.MockICardApp.data.model.entity.Employee;
import com.siemens.MockICardApp.data.model.entity.EmployeeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DTOConverter {
    @Autowired
    public static Employee fromCreateEmployeeDTOToEmployee(EmployeeWriteDTO employeeWriteDTO) {
        Employee employee = new Employee();
        employee.setName(employeeWriteDTO.getName());
        employee.setSurname(employeeWriteDTO.getSurname());
        employee.setCompany(employeeWriteDTO.getCompany());
        employee.setBuilding(employeeWriteDTO.getBuilding());
        employee.setCreatedAt(String.valueOf(System.currentTimeMillis()));
        employee.setUpdatedAt(String.valueOf(System.currentTimeMillis()));
        return employee;
    }

    public static EmployeeReadDTO fromEmployeeToReadEmployeeDTO(Employee employee) {
        EmployeeReadDTO employeeReadDTO = new EmployeeReadDTO();
        employeeReadDTO.setId(employee.getId());
        employeeReadDTO.setName(employee.getName());
        employeeReadDTO.setSurname(employee.getSurname());
        employeeReadDTO.setCompany(employee.getCompany());
        employeeReadDTO.setBuilding(employee.getBuilding());
        employeeReadDTO.setCreatedAt(employee.getCreatedAt());
        employeeReadDTO.setUpdatedAt(employee.getUpdatedAt());
        if (employee.getEmployeeEvents() != null) {
            List<EmployeeEventReadDTO> employeeEventReadDTOs = employee.getEmployeeEvents().stream()
                    .map(DTOConverter::fromEmployeeEventToEmployeeEventReadDTO)
                    .collect(Collectors.toList());
            employeeReadDTO.setEmployeeEvents(employeeEventReadDTOs);
        }
        return employeeReadDTO;
    }

    public static EmployeeEvent fromEmployeeEventCreateDTOToEmployeeEvent(Employee employee, EmployeeEventWriteDTO employeeEventWriteDTO) {
        EmployeeEvent employeeEvent = new EmployeeEvent();
        employeeEvent.setBuilding(employeeEventWriteDTO.getBuilding());

        // Convert the event time format
        String eventTime = employeeEventWriteDTO.getEventTime();
        String formattedTime = LocalDateTime.parse(eventTime, DateTimeFormatter.ISO_DATE_TIME)
                .format(DateTimeFormatter.ofPattern("HHmmss"));

        // Get the date part and remove hyphens
        String datePart = eventTime.substring(0, 10).replace("-", "");

        // Concatenate date and formatted time
        String convertedEventTime = formattedTime + datePart;
        employeeEvent.setEventTime(convertedEventTime);

        employeeEvent.setEmployee(employee);
        employee.addEvent(employeeEvent);
        return employeeEvent;
    }

    public static EmployeeEventReadDTO fromEmployeeEventToEmployeeEventReadDTO(EmployeeEvent employeeEvent) {
        EmployeeEventReadDTO employeeEventReadDTO = new EmployeeEventReadDTO();
        employeeEventReadDTO.setId(employeeEvent.getId());
        employeeEventReadDTO.setBuilding(employeeEvent.getBuilding());
        employeeEventReadDTO.setEventTime(employeeEvent.getEventTime());
        return employeeEventReadDTO;
    }
}
