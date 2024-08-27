package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceImplTest {

    /*
    In more complex enterprise applications these logic-oriented service tests end up saving us from several gaps that would
    otherwise become bugs.
     */

    @Mock
    EmployeeRepository employeeRepository;
    @InjectMocks
    EmployeeServiceImpl employeeServiceImpl;

    @Test
    public void testReadEmployee() {
        Employee testEmployee = new Employee();
        testEmployee.setEmployeeId("testId");
        Employee directReport = new Employee();
        directReport.setEmployeeId("directReportId");
        testEmployee.setDirectReports(Arrays.asList(directReport));

        when(employeeRepository.findByEmployeeId(anyString())).thenReturn(testEmployee);
        when(employeeRepository.findAllByEmployeeIdIn(anyList())).thenReturn(Arrays.asList(directReport));

        Employee result = employeeServiceImpl.readEmployee(testEmployee.getEmployeeId());

        verify(employeeRepository).findAllByEmployeeIdIn(anyList());
        verify(employeeRepository).findByEmployeeId(anyString());
    }

    @Test
    public void testUpdateEmployee() {

    }

    @Test
    public void testReadReportingStructure() {
        Employee testEmployee = new Employee();
        testEmployee.setEmployeeId("testId");
        testEmployee.setDepartment("Hydrology");
        Employee directReport = new Employee();
        directReport.setEmployeeId("directReportId");
        testEmployee.setDirectReports(Arrays.asList(directReport));

        when(employeeRepository.findByEmployeeId(anyString())).thenReturn(testEmployee);

        ReportingStructure result = employeeServiceImpl.readReportingStructure(testEmployee.getEmployeeId());

        assertEquals(1, result.getNumberOfReports());

        verify(employeeRepository).findByEmployeeId(anyString());
    }
}
