package com.arvind.simplilearn.aws.dynamodb.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.arvind.simplilearn.aws.dynamodb.model.EmployeePayroll;

@Repository
public class PayrollDAOImpl implements PayrollDao {

	@Autowired
	private DynamoDBMapper dynamoDBMapper;
		
	@Override
	public EmployeePayroll read(String employeeId) {
		return dynamoDBMapper.load(EmployeePayroll.class, employeeId);
	}

	@Override
	public List<EmployeePayroll> readAll() {
		return dynamoDBMapper.scan(EmployeePayroll.class, new DynamoDBScanExpression());
	}

}
