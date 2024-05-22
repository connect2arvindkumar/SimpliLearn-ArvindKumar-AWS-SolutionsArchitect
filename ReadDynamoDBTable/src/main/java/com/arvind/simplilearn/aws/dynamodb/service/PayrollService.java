package com.arvind.simplilearn.aws.dynamodb.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.arvind.simplilearn.aws.dynamodb.model.EmployeePayroll;

@Service
public interface PayrollService {
	
public EmployeePayroll read(String employeeId);
	
	public List<EmployeePayroll> readAll();

}
