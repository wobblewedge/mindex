package com.mindex.challenge.dao;

import com.mindex.challenge.data.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
    Employee findByEmployeeId(String employeeId);

    List<Employee> findAllByEmployeeIdIn(List<String> employeeIds);

    /*
    I expect we'll need this in order to look up employees by department for more general, less employee-specific
    reportingStructure querying.
    */
    Employee findByDepartment(String department);
}
