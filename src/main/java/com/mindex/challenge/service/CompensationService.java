package com.mindex.challenge.service;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;

public interface CompensationService {
    Compensation read(String employeeId);

    Employee create(String employeeId, Compensation compensation);
}
