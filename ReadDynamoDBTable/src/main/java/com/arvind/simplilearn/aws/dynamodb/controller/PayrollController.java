package com.arvind.simplilearn.aws.dynamodb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.arvind.simplilearn.aws.dynamodb.model.EmployeePayroll;
import com.arvind.simplilearn.aws.dynamodb.service.PayrollService;

@RestController
public class PayrollController {

	@Autowired
	PayrollService service;

	@GetMapping("/payroll/read/{employeeId}")
	public ResponseEntity<EmployeePayroll> read(@PathVariable String employeeId) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(service.read(employeeId));
		} catch (AmazonServiceException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
		} catch (AmazonClientException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}
	
	@GetMapping("/payroll/readAll")
	public ResponseEntity<List<EmployeePayroll>> readAll() {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(service.readAll());
		} catch (AmazonServiceException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
		} catch (AmazonClientException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}
}
