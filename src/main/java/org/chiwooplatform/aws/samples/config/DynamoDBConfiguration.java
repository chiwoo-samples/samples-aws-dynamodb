package org.chiwooplatform.aws.samples.config;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

@Configuration
@EnableDynamoDBRepositories(basePackages = "org.chiwooplatform.aws.samples.repository.dynamodb")
public class DynamoDBConfiguration {

    @Value("${amazon.aws.access-key-id}")
    private String accessKey;

    @Value("${amazon.aws.access-key-secret}")
    private String secretKey;

    @Value("${amazon.aws.dynamodb.reason}")
    private String regionName;

    @Value("${amazon.aws.dynamodb.endpoint:}")
    private String serviceEndpoint;

    private AWSCredentialsProvider awsCredentialsProvider() {
        System.out.println(String.format("accessKey: '%s'", accessKey));
        System.out.println(String.format("secretKey: '%s'", secretKey));
        final BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        final AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);
        return credentialsProvider;
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        // System.out.println("serviceEndpoint: " + serviceEndpoint);
        // System.out.println("regionName: " + regionName);
        // System.out.println("Regions: " + region);
        Regions region = Regions.fromName(regionName);
        // System.out.println(String.format("regionName: '%s'", regionName));
        AmazonDynamoDBClientBuilder builder = AmazonDynamoDBClientBuilder.standard().withCredentials(awsCredentialsProvider())
                .withRegion(region);
        if (!StringUtils.isEmpty(serviceEndpoint)) {
            System.out.println(String.format("serviceEndpoint: '%s'", serviceEndpoint));
            EndpointConfiguration configuration = new EndpointConfiguration(serviceEndpoint, regionName);
            builder.withEndpointConfiguration(configuration);
        }
        AmazonDynamoDB dynamoDB = builder.build();
        return dynamoDB;
    }
}
