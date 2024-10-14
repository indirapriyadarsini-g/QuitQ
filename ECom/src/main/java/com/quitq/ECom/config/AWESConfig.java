package com.quitq.ECom.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;

@Configuration
public class AWESConfig {
	@Value("${aws.region-id}")
	private String region;
	@Value("${aws.ses.access-key}")

	private String accessKey;
	@Value("${aws.ses.secret-key}")

	private String secretKey;
			
@Bean
AmazonSimpleEmailService getSESClient() {
	return AmazonSimpleEmailServiceClientBuilder.standard()
			.withRegion(region)
			.withCredentials(getCredentials())
			.build();
	
}
@Bean
AWSCredentialsProvider getCredentials() {
	AWSCredentials awsCredentials=new BasicAWSCredentials(accessKey,secretKey);
	return new AWSStaticCredentialsProvider(awsCredentials);
	
}

}

