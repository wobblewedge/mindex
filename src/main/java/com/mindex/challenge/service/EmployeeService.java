package com.mindex.challenge.service;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;

public interface EmployeeService {
    Employee createEmployee(Employee employee);

    Employee readEmployee(String id);

    ReportingStructure readReportingStructure(String id);

    Employee updateEmployee(Employee employee);
}
