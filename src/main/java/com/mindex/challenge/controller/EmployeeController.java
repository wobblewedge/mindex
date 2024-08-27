package com.mindex.challenge.controller;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/employee")
    public Employee create(@RequestBody Employee employee) {
        LOG.debug("Received employee create request for [{}]", employee);

        return employeeService.createEmployee(employee);
    }

    @GetMapping("/employee/{id}")
    public Employee readEmployee(@PathVariable String id) {
        LOG.debug("Received employee read request for id [{}]", id);

        return employeeService.readEmployee(id);
    }

    @GetMapping("/employee/{id}/reportingStructure")
    public ReportingStructure readReportingStructure(@PathVariable String id) {
        LOG.debug("Received employee read reporting structure request for id [{}]", id);

        return employeeService.readReportingStructure(id);
    }

    @PutMapping("/employee/{id}")
    public Employee updateEmployee(@PathVariable String id, @RequestBody Employee employee) {
        LOG.debug("Received employee update request for id [{}] and employee [{}]", id, employee);

        employee.setEmployeeId(id);
        return employeeService.updateEmployee(employee);
    }
}
