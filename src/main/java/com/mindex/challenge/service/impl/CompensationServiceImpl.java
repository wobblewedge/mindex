package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import org.springframework.stereotype.Service;

@Service
public class CompensationServiceImpl implements CompensationService {
    private EmployeeRepository employeeRepository;

    public CompensationServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Compensation read(String employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        if (employee != null && employee.getCompensation() != null) {
            return employee.getCompensation();
        } else {
            throw new RuntimeException("Employee not found: " + employeeId);
        }
    }

    @Override
    public Employee create(String employeeId, Compensation compensation) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        if (employee != null) {
            employee.setCompensation(compensation);
            return employeeRepository.save(employee);
        } else {
            throw new RuntimeException("Employee not found: " + employeeId);
        }
    }

}
