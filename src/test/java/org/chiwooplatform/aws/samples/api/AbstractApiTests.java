package org.chiwooplatform.aws.samples.api;

import org.chiwooplatform.aws.samples.AbstractSpringTests;
import org.chiwooplatform.aws.samples.config.ApplicationConfiguration;
import org.chiwooplatform.aws.samples.config.WebConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Import({ ApplicationConfiguration.class, WebConfiguration.class })
@ComponentScan(basePackages = { "com.sec.ppmt.collector.repository" })
public abstract class AbstractApiTests extends AbstractSpringTests {

    @Autowired
    protected MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    protected String toJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

}
