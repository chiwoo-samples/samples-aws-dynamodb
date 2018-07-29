package org.chiwooplatform.aws.samples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, TransactionAutoConfiguration.class,
        JdbcTemplateAutoConfiguration.class })
public class DynamoDBApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DynamoDBApplication.class, args);
    }

}
