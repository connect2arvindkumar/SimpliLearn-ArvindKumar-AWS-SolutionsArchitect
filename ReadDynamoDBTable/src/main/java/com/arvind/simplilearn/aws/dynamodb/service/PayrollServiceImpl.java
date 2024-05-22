package com.arvind.simplilearn.aws.dynamodb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arvind.simplilearn.aws.dynamodb.dao.PayrollDao;
import com.arvind.simplilearn.aws.dynamodb.model.EmployeePayroll;

@Service
public class PayrollServiceImpl implements PayrollService {

	@Autowired
	PayrollDao dao;
	
	@Override
	public EmployeePayroll read(String employeeId) {
		return dao.read(employeeId);
	}

	@Override
	public List<EmployeePayroll> readAll() {
		return dao.readAll();
	}

}
