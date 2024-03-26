package com.siemens.MockICardApp.data.dto.converter;

import com.siemens.MockICardApp.data.dto.EmployeeCreateDTO;
import com.siemens.MockICardApp.data.dto.EmployeeEventCreateDTO;
import com.siemens.MockICardApp.data.dto.EmployeeEventReadDTO;
import com.siemens.MockICardApp.data.dto.EmployeeReadDTO;
import com.siemens.MockICardApp.data.model.entity.Employee;
import com.siemens.MockICardApp.data.model.entity.EmployeeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DTOConverter {
    @Autowired
    public static Employee fromCreateEmployeeDTOToEmployee(EmployeeCreateDTO employeeCreateDTO) {
        Employee employee = new Employee();
        employee.setName(employeeCreateDTO.getName());
        employee.setSurname(employeeCreateDTO.getSurname());
        employee.setCompany(employeeCreateDTO.getCompany());
        employee.setBuilding(employeeCreateDTO.getBuilding());
        employee.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        employee.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
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
        List<EmployeeEventReadDTO> employeeEventReadDTOs = employee.getEmployeeEvents().stream()
                .map(DTOConverter::fromEmployeeEventToEmployeeEventReadDTO)
                .collect(Collectors.toList());

        employeeReadDTO.setEmployeeEvents(employeeEventReadDTOs);
        return employeeReadDTO;
    }

    public static EmployeeEvent fromEmployeeEventCreateDTOToEmployeeEvent(Employee employee, EmployeeEventCreateDTO employeeEventCreateDTO) {
        EmployeeEvent employeeEvent = new EmployeeEvent();
        employeeEvent.setBuilding(employeeEventCreateDTO.getBuilding());
        employeeEvent.setEventTime(employeeEventCreateDTO.getEventTime());
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
