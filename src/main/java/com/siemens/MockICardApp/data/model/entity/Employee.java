package com.siemens.MockICardApp.data.model.entity;

import com.siemens.MockICardApp.data.enums.Building;
import com.siemens.MockICardApp.data.enums.Company;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employee")
@AllArgsConstructor
@NoArgsConstructor
@Setter  @Getter
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private String id;

    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(255)")
    private String name;

    @Column(name = "surname", nullable = false, columnDefinition = "VARCHAR(255)")
    private String surname;

    @Column(name = "company", nullable = false, columnDefinition = "VARCHAR(255)")
    @Enumerated(EnumType.STRING)
    private Company company;

    @Column(name = "building", nullable = false, columnDefinition = "VARCHAR(255)")
    @Enumerated(EnumType.STRING)
    private Building building;

    @Column(name = "createdAt", updatable = false, nullable = false)
    private String createdAt;

    @Column(name = "updatedAt", nullable = false)
    private String updatedAt;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<EmployeeEvent> employeeEvents;

    public void addEvent(EmployeeEvent event) {
        if (employeeEvents == null) {
            employeeEvents = new ArrayList<>();
        }
        employeeEvents.add(event);
    }
}

