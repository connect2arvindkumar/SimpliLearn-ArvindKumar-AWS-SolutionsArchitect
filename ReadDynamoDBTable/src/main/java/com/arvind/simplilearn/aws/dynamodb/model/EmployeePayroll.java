package com.arvind.simplilearn.aws.dynamodb.model;

import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
@Component
@DynamoDBTable(tableName = "TelemaxPayrollTable")
public class EmployeePayroll {

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
