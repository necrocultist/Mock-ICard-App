package com.siemens.MockICardApp.data.model.entity;
import com.siemens.MockICardApp.data.enums.Building;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "employee_event")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class EmployeeEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "building", nullable = false, columnDefinition = "VARCHAR(255)")
    private Building building;

    @Column(name = "event_time", nullable = false)
    private Timestamp eventTime;

    @JoinColumn(name = "employee")
    @ManyToOne(fetch = FetchType.LAZY)
    private Employee employee;
}
