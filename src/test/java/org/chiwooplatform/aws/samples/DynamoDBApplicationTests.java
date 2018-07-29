package org.chiwooplatform.aws.samples;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@SpringBootTest(classes = DynamoDBApplication.class)
public class DynamoDBApplicationTests extends AbstractSpringTests {

    @Autowired
    private Environment environment;

    @Test
    public void contextLoads() {

        System.out.println("environment.getProperty('host') --- " + environment.getProperty("hostname"));
        System.out.println("System.getProperty('host') --- " + System.getProperty("host"));

    }

}
