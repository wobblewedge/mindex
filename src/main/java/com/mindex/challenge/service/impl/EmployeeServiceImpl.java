package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    //Changed to constructor injection, which is something I would do in an enterprise app for thread-safety purposes.
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee createEmployee(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }

    @Override
    public Employee readEmployee(String id) {
        LOG.debug("Retrieving employee with id [{}]", id);

        /*
        This endpoint was only mapping employeeId for the directReports object. These changes mean it will
        provide more complete data for the reports of a given employee. There's likely a way to do this without hitting
        the repository twice, which I would prefer not to do. This is my first time using mongo, so I'll settle for this.
         */
        if (id != null) {
            Employee employee = employeeRepository.findByEmployeeId(id);
            if (!CollectionUtils.isEmpty(employee.getDirectReports())) {
                List<String> employeeIds = employee.getDirectReports().stream()
                        .map(Employee::getEmployeeId).collect(Collectors.toList());
                List<Employee> directReports = employeeRepository.findAllByEmployeeIdIn(employeeIds);
                employee.setDirectReports(directReports);
            }
            return employee;
        } else {
            throw new RuntimeException("Invalid employeeId: " + id);
        }
    }

    @Override
    public ReportingStructure readReportingStructure(String id) {
        LOG.debug("Retrieving employee reporting structure with id [{}]", id);

        if (id != null) {
            Employee employee = employeeRepository.findByEmployeeId(id);
            return buildReportingStructure(employee);
        } else {
            throw new RuntimeException("Invalid employeeId: " + id);
        }
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);
        Employee existingEmployee = employeeRepository.findByEmployeeId(employee.getEmployeeId());
        if (existingEmployee != null) {
            return employeeRepository.save(employee);
        } else {
            throw new RuntimeException("Employee does not exist: " + employee.getEmployeeId());
        }
    }

    private ReportingStructure buildReportingStructure(Employee employee) {
        ReportingStructure reportingStructure = new ReportingStructure();
        reportingStructure.setEmployeeId(employee.getEmployeeId());
        reportingStructure.setDepartment(employee.getDepartment());
        /*
        Since more thorough directReport data is represented in the readEmployee method, I'll use this as a
        summation and only list the employeeIds.
         */
        List<Employee> directReports = employee.getDirectReports();
        if (!CollectionUtils.isEmpty(directReports)) {
            reportingStructure.setNumberOfReports(directReports.size());
            reportingStructure.setDirectReportIds(directReports
                    .stream().map(Employee::getEmployeeId)
                    .collect(Collectors.toList()));
        } else {
            reportingStructure.setNumberOfReports(0);
        }
        return reportingStructure;
    }
}
