package com.siemens.MockICardApp.service;

import com.siemens.MockICardApp.data.dto.EmployeeWriteDTO;
import com.siemens.MockICardApp.data.dto.EmployeeReadDTO;
import com.siemens.MockICardApp.data.dto.converter.DTOConverter;
import com.siemens.MockICardApp.data.model.entity.Employee;
import com.siemens.MockICardApp.data.model.event.LogEvent;
import com.siemens.MockICardApp.kafka.producer.LogEventProducer;
import com.siemens.MockICardApp.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LogEventProducer logEventProducer;

    public EmployeeReadDTO createEmployee(EmployeeWriteDTO employeeWriteDTO) {
        try {
            Employee employee = DTOConverter.fromCreateEmployeeDTOToEmployee(employeeWriteDTO);
            employeeRepository.save(employee);
            logEventProducer.sendMessage(new LogEvent(1, "Device event added for user " + employee.getId() + " at " + employee.getUpdatedAt()));
            return DTOConverter.fromEmployeeToReadEmployeeDTO(employee);
        } catch (Exception e) {
            System.out.println("An error occurred during employee creation: " + e.getMessage());
            throw e;
        }
    }


    public EmployeeReadDTO updateEmployee(String id, EmployeeWriteDTO employeeWriteDTO) {
        try {
            if (employeeRepository.existsById(id)) {
                Employee employee = employeeRepository.getById(id);
                employee.setName(employeeWriteDTO.getName());
                employee.setSurname(employeeWriteDTO.getSurname());
                employee.setCompany(employeeWriteDTO.getCompany());
                employee.setBuilding(employeeWriteDTO.getBuilding());
                employee.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                employeeRepository.save(employee);
                logEventProducer.sendMessage(new LogEvent(2, "Device event added for user " + employee.getId() + " at " + employee.getUpdatedAt()));
                return DTOConverter.fromEmployeeToReadEmployeeDTO(employee);
            } else {
                throw new EntityNotFoundException("Employee with id " + id + " does not exist.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred during employee update: " + e.getMessage());
            throw e;
        }
    }

    public void deleteEmployee(String id) {
        try {
            if (employeeRepository.existsById(id)) {
                employeeRepository.deleteById(id);
            }
        } catch (Exception e) {
            System.out.println("An error occurred during employee deletion: " + e.getMessage());
            throw e;
        }
    }

    public List<EmployeeReadDTO> getEmployeeList() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(DTOConverter::fromEmployeeToReadEmployeeDTO)
                .collect(Collectors.toList());
    }

    public EmployeeReadDTO getEmployeeById(String id) {
        try {
            Employee employee = employeeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
            return DTOConverter.fromEmployeeToReadEmployeeDTO(employee);
        } catch (EntityNotFoundException e) {
            System.out.println("An error occurred during employee event retrieval: " + e.getMessage());
            throw e;
        }
    }
}