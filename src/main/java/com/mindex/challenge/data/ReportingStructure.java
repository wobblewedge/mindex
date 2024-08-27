package com.mindex.challenge.data;


import java.util.List;

/*
Typically I would use @Builder and @Data here and in other pojos,
 which are lombok annotations that cut down on boilerplate created by getters/setters.
 */
public class ReportingStructure {
    private String employeeId;
    private int numberOfReports;
    private String department;
    private List<String> directReportIds;


    public List<String> getDirectReportIds() {
        return directReportIds;
    }

    public void setDirectReportIds(List<String> directReportIds) {
        this.directReportIds = directReportIds;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public int getNumberOfReports() {
        return numberOfReports;
    }

    public void setNumberOfReports(int numberOfReports) {
        this.numberOfReports = numberOfReports;
    }
}
