package org.chiwooplatform.aws.samples;

import java.util.Arrays;

import org.chiwooplatform.aws.samples.support.Utils;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
public abstract class AbstractSpringTests {

    protected final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Environment environment;

    protected String getActiveProfiles() {
        return Arrays.toString(environment.getActiveProfiles());
    }

    protected Environment getEnvironment() {
        return environment;
    }

    @BeforeAll
    public static void springBeforeAll() {
        System.setProperty("hostname", Utils.hostname());
    }

}
