package org.chiwooplatform.aws.samples.api;

import org.chiwooplatform.aws.samples.AbstractSpringTests;
import org.chiwooplatform.aws.samples.DynamoDBApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc(secure = false)
@SpringBootTest(classes = DynamoDBApplication.class, webEnvironment = WebEnvironment.MOCK)
public class AbstractMockMvcTests extends AbstractSpringTests {

    @Autowired
    protected MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    protected String toJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

}
