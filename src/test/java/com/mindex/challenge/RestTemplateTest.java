package com.mindex.challenge;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestTemplateTest {

    private String employeeUrl;
    private String employeeIdUrl;
    private String reportingStructureUrl;
    private String compensationUrl;
    private static Employee testEmployee;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    /*
    I broke up the test method because in order to provide more detailed and granular testing.
     */
    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee";
        employeeIdUrl = "http://localhost:" + port + "/employee/{id}";
        reportingStructureUrl = "http://localhost:" + port + "/employee/{id}/reportingStructure";
        compensationUrl = "http://localhost:" + port + "/compensation/{id}";
        testEmployee =
                createTestEmployee("John", "Doe", "Engineering",
                        "Developer", null);
    }

    @Test
    public void testCreateEmployee() {
        // Create checks
        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();
        assertNotNull(createdEmployee.getEmployeeId());
        assertEmployeeEquivalence(testEmployee, createdEmployee);
    }

    @Test
    public void testReadEmployee() {
        testEmployee = createTestEmployee("John", "Lennon", "Engineering",
                "Development Manager", "16a596ae-edd3-4847-99fe-c4518e82c86f");
        // Read checks
        Employee readEmployee = restTemplate
                .getForEntity(employeeIdUrl, Employee.class, testEmployee.getEmployeeId()).getBody();
        assertEquals(testEmployee.getEmployeeId(), readEmployee.getEmployeeId());
        assertEmployeeEquivalence(testEmployee, readEmployee);
    }

    @Test
    public void testReadEmployeeReportingStructure() {
        testEmployee = createTestEmployee("John", "Lennon", "Engineering",
                "Development Manager", "16a596ae-edd3-4847-99fe-c4518e82c86f");
        // Read reporting structure for an employee with direct reports
        ReportingStructure reportingStructure = restTemplate
                .getForEntity(reportingStructureUrl, ReportingStructure.class, testEmployee.getEmployeeId()).getBody();
        assertEquals(testEmployee.getEmployeeId(), reportingStructure.getEmployeeId());
        assertEquals(2, reportingStructure.getNumberOfReports());
    }

    @Test
    public void testReadEmployeeReportingStructure_noReportees() {
        restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();
        // Read reporting structure for an employee with no direct reports
        ReportingStructure reportingStructure = restTemplate
                .getForEntity(reportingStructureUrl, ReportingStructure.class, testEmployee.getEmployeeId()).getBody();
        assertEquals(testEmployee.getEmployeeId(), reportingStructure.getEmployeeId());
        assertNull(testEmployee.getDirectReports());
    }

    @Test
    public void testUpdateEmployee() {
        testEmployee = createTestEmployee("John", "Lennon", "Engineering",
                "Development Manager", "16a596ae-edd3-4847-99fe-c4518e82c86f");
        Employee readEmployee = restTemplate.getForEntity(employeeIdUrl, Employee.class, testEmployee.getEmployeeId()).getBody();
        // Update employee checks
        testEmployee.setPosition("Junior Developer");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Employee updatedEmployee =
                restTemplate.exchange(employeeIdUrl,
                        HttpMethod.PUT,
                        new HttpEntity<Employee>(readEmployee, headers),
                        Employee.class,
                        readEmployee.getEmployeeId()).getBody();

        assertEmployeeEquivalence(readEmployee, updatedEmployee);
    }

    @Test
    public void testPutCompensation() {
        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();
        assertNull(createdEmployee.getCompensation());

        Compensation testCompensation = createTestCompensation();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        //Add compensation to employee.
        Compensation result = restTemplate.exchange(compensationUrl,
                HttpMethod.PUT,
                new HttpEntity<Compensation>(testCompensation, headers),
                Compensation.class,
                createdEmployee.getEmployeeId()).getBody();
        assertEquals(result.getBonusPercentage(), testCompensation.getBonusPercentage());
        assertEquals(result.getEffectiveDate(), testCompensation.getEffectiveDate());
        assertNotNull(result.getSalary());
    }

    private Compensation createTestCompensation() {
        Compensation compensation = new Compensation();
        compensation.setSalary(20.00);
        compensation.setEffectiveDate(new Date());
        compensation.setBonusPercentage(5);
        return compensation;
    }

    private Employee createTestEmployee(String firstName, String lastName, String department, String position,
                                        String employeeId) {
        Employee testEmployee = new Employee();
        if (employeeId != null) {
            testEmployee.setEmployeeId(employeeId);
        }
        testEmployee.setFirstName(firstName);
        testEmployee.setLastName(lastName);
        testEmployee.setDepartment(department);
        testEmployee.setPosition(position);
        return testEmployee;
    }

    private static void assertEmployeeEquivalence(Employee expected, Employee actual) {
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getDepartment(), actual.getDepartment());
        assertEquals(expected.getPosition(), actual.getPosition());
    }
}
