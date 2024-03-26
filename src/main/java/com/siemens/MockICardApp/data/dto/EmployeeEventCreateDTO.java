package com.siemens.MockICardApp.data.dto;

import com.siemens.MockICardApp.data.enums.Building;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
public class EmployeeEventCreateDTO {
    private Building building;
    private Timestamp eventTime;
}
