package com.arvind.simplilearn.aws.publishdata.lambda.function;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;
import com.amazonaws.services.kinesis.model.PutRecordRequest;
import com.amazonaws.services.kinesis.model.PutRecordResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;

public class PublishDataKinesisDataStream implements RequestHandler<S3Event, String> {

	private static final String STREAM_NAME = "TelemaxPayrollStream";

	String accessKey = "AKIARYQOKWRRWXS5XQXO";
    String secretKey = "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";
	
    @Override
	public String handleRequest(S3Event s3Event, Context context) {
		System.out.println("Inside PublishDataKinesisDataStreamLambdaFunction.handleRequest");
				
		System.out.println("AmazonKinesisClientBuilder - Start");		
		AmazonKinesis amazonKinesis = AmazonKinesisClientBuilder.standard().withRegion("us-east-1")
				.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
				.build();
		
		System.out.println("AmazonKinesis - Created Sucessfully");	
		
		
		System.out.println("Publishing records from csv file in S3 bucket to AmazonKinesis - Start");		
		for (S3EventNotification.S3EventNotificationRecord record : s3Event.getRecords()) {
			String bucketName = record.getS3().getBucket().getName();
			String objectKey = record.getS3().getObject().getKey();

			System.out.println("bucketName " + bucketName);
			System.out.println("objectKey " + objectKey);
			
			try {
				AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();
				S3Object s3Object = s3Client.getObject(bucketName, objectKey);

				try (InputStream inputStream = s3Object.getObjectContent();
	                     BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
	                    String line;
	                    while ((line = reader.readLine()) != null) {
	                        // Generate unique numeric primary key for each record
	                        String primaryKey = generatePrimaryKey();

	                        PutRecordRequest putRecordRequest = new PutRecordRequest()
	                                .withStreamName(STREAM_NAME)
	                                .withPartitionKey(primaryKey)
	                                .withData(ByteBuffer.wrap(line.getBytes()));
	                        PutRecordResult putRecordResult = amazonKinesis.putRecord(putRecordRequest);
	                        //System.out.println("Published record to Kinesis. Sequence number: " + putRecordResult.getSequenceNumber());
	                    }
	                }
			} catch (Exception e) {
				e.printStackTrace();
				
				System.out.println("Error processing CSV file and publishing to Kinesis: " + e.getMessage());
				
				return "Error processing CSV file and publishing to Kinesis: " + e.getMessage();
			}
		}
		
		System.out.println("Publishing records from csv file in S3 bucket to AmazonKinesis - Sucessfully Completed");
		
		return "Successfully processed CSV file and published to Kinesis.";
	}

	private String generatePrimaryKey() {
		return String.valueOf(Math.abs(UUID.randomUUID().getLeastSignificantBits()));
	}
}
