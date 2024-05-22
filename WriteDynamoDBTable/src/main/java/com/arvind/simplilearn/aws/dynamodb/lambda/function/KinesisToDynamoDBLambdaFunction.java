package com.arvind.simplilearn.aws.dynamodb.lambda.function;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.KinesisEvent;
import com.arvind.simplilearn.aws.dynamodb.model.EmployeePayroll;
import com.fasterxml.jackson.databind.ObjectMapper;


public class KinesisToDynamoDBLambdaFunction implements RequestHandler<KinesisEvent, Void> {

	private static final String TABLE_NAME = "TelemaxPayrollTable";    
	String accessKey = "AKIARYQOKWRRWXS5XQXO";
    String secretKey = "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";
	
	private AmazonDynamoDB getAmazonDynamoDB() {
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
				.withRegion(Regions.US_EAST_1).build();

		return client;
	}
    
	private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public Void handleRequest(KinesisEvent event, Context context) {
    	System.out.println("Inside KinesisToDynamoDBLambdaFunction.handleRequest");
    	
    	if(event.getRecords() != null) {
    	
	    	try {
	            for (KinesisEvent.KinesisEventRecord record : event.getRecords()) {
	                String data = new String(record.getKinesis().getData().array());
	                
	                String jsonData = convertToJSON(data);
	                
	                record.getKinesis().getPartitionKey();
	                
	                EmployeePayroll payrollRecord = objectMapper.readValue(jsonData, EmployeePayroll.class);
	                
	                writeDataToDynamoDB(record.getKinesis().getPartitionKey(), payrollRecord);
	            }
	        } catch (Exception e) {
	           e.printStackTrace();
	           
	           System.out.println("Error executing KinesisToDynamoDBLambdaFunction.handleRequest : " + e.getMessage());
	        }
	    	
	    	System.out.println("KinesisToDynamoDBLambdaFunction.handleRequest - execution completed sucessfully");
    	} else {
    		System.out.println("KinesisToDynamoDBLambdaFunction.handleRequest - No Data Available");
    	}
    	
    	return null;
    }
    
    private String convertToJSON(String data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private void writeDataToDynamoDB(String employeeId, EmployeePayroll payrollRecord) {
    	DynamoDB dynamoDB = new DynamoDB(getAmazonDynamoDB());
    	
    	try {
            Table table = dynamoDB.getTable(TABLE_NAME);
            Item item = new Item().withPrimaryKey("employeeId", employeeId);
            
            item.with("name", payrollRecord.getName());
            item.with("department", payrollRecord.getDepartment());
            item.with("jobTitle", payrollRecord.getJobTitle());
            item.with("typeFullTimeOrPartTime", payrollRecord.getTypeFullTimeOrPartTime());
            item.with("hireDate", payrollRecord.getHireDate());
            item.with("hourlyRate", payrollRecord.getHourlyRate());
            item.with("regularPay", payrollRecord.getRegularPay());
            item.with("overtimePay", payrollRecord.getOvertimePay());
            item.with("otherPay", payrollRecord.getOtherPay());
  
            table.putItem(item);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error executing KinesisToDynamoDBLambdaFunction.writeDataToDynamoDB : " + e.getMessage());
        }
    }
    
}
