package com.arvind.simplilearn.aws.dynamodb.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.arvind.simplilearn.aws.dynamodb.model.EmployeePayroll;

@Repository
public interface PayrollDao {

	public EmployeePayroll read(String employeeId);
	
	public List<EmployeePayroll> readAll();
}
