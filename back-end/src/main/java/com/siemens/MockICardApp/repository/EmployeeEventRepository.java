package com.siemens.MockICardApp.repository;

import com.siemens.MockICardApp.data.model.entity.EmployeeEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeEventRepository extends JpaRepository<EmployeeEvent, String> {
    List<EmployeeEvent> findByEmployeeId(String employeeId);
}
