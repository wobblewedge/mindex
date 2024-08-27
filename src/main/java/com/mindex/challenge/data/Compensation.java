package com.mindex.challenge.data;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
public class Compensation {

    private double salary;
    private Date effectiveDate;
    private int bonusPercentage;


    public int getBonusPercentage() {
        return bonusPercentage;
    }

    public void setBonusPercentage(int bonusPercentage) {
        this.bonusPercentage = bonusPercentage;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
