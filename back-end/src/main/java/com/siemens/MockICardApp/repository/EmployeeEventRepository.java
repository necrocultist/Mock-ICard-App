package com.siemens.MockICardApp.repository;

import com.siemens.MockICardApp.data.model.entity.EmployeeEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface EmployeeEventRepository extends JpaRepository<EmployeeEvent, String> {
    List<EmployeeEvent> findByEmployeeId(String employeeId);

    @Query("SELECT e FROM EmployeeEvent e WHERE e.employee.id = :employeeId AND e.eventTime BETWEEN :startTime AND :endTime")
    List<EmployeeEvent> findByEmployeeIdAndTimeRange(String employeeId, String startTime, String endTime);

    List<EmployeeEvent> findByEmployeeIdAndEventTime(String employeeId, String formattedSelectedDay);
}
