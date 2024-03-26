package com.siemens.MockICardApp.graphql.inputOutput;

import lombok.Getter;

@Getter
public class AddEmployeeEventInput {
    private final String building;
    private final String eventTime;
    private final String employeeId;

    public AddEmployeeEventInput(String employeeId, String building, String eventDateTime) {
        this.employeeId = employeeId;
        this.building = building;
        this.eventTime = eventDateTime;
    }
}