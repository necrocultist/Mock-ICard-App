package com.siemens.MockICardApp.data.dto;

import com.siemens.MockICardApp.data.enums.Building;
import com.siemens.MockICardApp.data.enums.Company;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
public class EmployeeCreateDTO {
    private String name;
    private String surname;
    private Company company;
    private Building building;
}
