package com.siemens.MockICardApp.data.dto;

import com.siemens.MockICardApp.data.enums.Building;
import com.siemens.MockICardApp.data.enums.Company;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EmployeeReadDTO {
    private String id;
    private String name;
    private String surname;
    private Company company;
    private Building building;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private List<EmployeeEventReadDTO> employeeEvents;
}
