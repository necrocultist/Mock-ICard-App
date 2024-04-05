package com.siemens.MockICardApp.data.model.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
public class DeviceEvent {
    private String building;
    private String eventTime;
    private String employeeId;
}
