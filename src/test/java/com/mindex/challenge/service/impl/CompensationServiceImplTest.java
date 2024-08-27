package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CompensationServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private CompensationServiceImpl compensationService;

    @Test
    public void testRead() {
        Employee employee = new Employee();
        /*
        Normally it's nice to have a utility in the test folder responsible for creating the abundance of test objects
        that tend to be necessary in an enterprise application.
         */
        employee.setEmployeeId("testId");
        Compensation compensation = new Compensation();
        compensation.setEffectiveDate(new Date());
        compensation.setSalary(105040.00);
        employee.setCompensation(compensation);
        when(employeeRepository.findByEmployeeId(anyString())).thenReturn(employee);
        Compensation result = compensationService.read(employee.getEmployeeId());

        assertEquals(employee.getCompensation().getEffectiveDate(), result.getEffectiveDate());
    }

    @Test
    public void testRead_notFound() {
        //Repository will return nothing.
        assertThrows(RuntimeException.class, () ->
                compensationService.read("fakeId"));
    }
}
