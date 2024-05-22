package com.arvind.simplilearn.aws.dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
@DynamoDBTable(tableName = "TelemaxPayrollTable")
public class EmployeePayroll {
	
	
	public EmployeePayroll(String data) {
        String[] parts = data.split(",");
        if (parts.length != 9) {
            throw new IllegalArgumentException("Invalid data format");
        }

        this.name = parts[0];
        this.department = parts[1];
        this.jobTitle = parts[2];
        this.typeFullTimeOrPartTime = parts[3];
        this.hireDate = parts[4];
        this.hourlyRate = parts[5];
        this.regularPay = parts[6];
        this.overtimePay = parts[7];
        this.otherPay = parts[8];
    }

	@DynamoDBHashKey(attributeName = "employeeId")
	private String employeeId;
	
	@DynamoDBAttribute(attributeName = "name")
	private String name;
	
	@DynamoDBAttribute(attributeName = "department")
	private String department;
	
	@DynamoDBAttribute(attributeName = "jobTitle")
	private String jobTitle;
	
	@DynamoDBAttribute(attributeName = "typeFullTimeOrPartTime")
	private String typeFullTimeOrPartTime;
	
	@DynamoDBAttribute(attributeName = "hireDate")
	private String hireDate;
	
	@DynamoDBAttribute(attributeName = "hourlyRate")
	private String hourlyRate;
	
	@DynamoDBAttribute(attributeName = "regularPay")
	private String regularPay;
	
	@DynamoDBAttribute(attributeName = "overtimePay")
	private String overtimePay;
	
	@DynamoDBAttribute(attributeName = "otherPay")
	private String otherPay;

}
