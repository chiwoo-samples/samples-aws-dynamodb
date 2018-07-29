package org.chiwooplatform.aws.samples.repository.dynamodb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Optional;
import java.util.UUID;

import org.chiwooplatform.aws.samples.model.CodeMapping;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import module.testbase.DynamoDBTestApplication;

@SpringJUnitConfig
@SpringBootTest(classes = DynamoDBTestApplication.class, webEnvironment = WebEnvironment.NONE)
public class CodeMappingRepositoryTest {

    protected final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CodeMappingRepository repository;

    protected CodeMapping model(final String id) {
        final String ifid = String.format("%s%s%s", UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                UUID.randomUUID().toString());
        final CodeMapping m = new CodeMapping();
        m.setId(id);
        m.setName("NAME");
        m.setIfid(ifid);
        m.setRegtime(System.currentTimeMillis());
        return m;
    }

    private static final String ID = "33013FEDD886E3C78E8727E78F2B097AE5";

    @Test
    public void ut1001_crud() throws Exception {
        String id = UUID.randomUUID().toString();
        logger.info("ID: {}", id);
        CodeMapping entity = model(id);
        repository.save(entity);
        Optional<CodeMapping> result = repository.findById(id);
        logger.info("result: {}", result.map(v -> v).orElse(null));
        assertEquals(id, result.get().getId());
        repository.deleteById(id);
        boolean exists = repository.existsById(id);
        assertFalse(exists);
    }

    @Test
    public void ut1002_add() throws Exception {
        String id = ID;
        logger.info("ID: {}", id);
        CodeMapping entity = model(id);
        repository.save(entity);
        Optional<CodeMapping> result = repository.findById(id);
        assertEquals(id, result.get().getId());
    }

    @Test
    public void ut1002_get() throws Exception {
        String id = ID;
        logger.info("ID: {}", id);
        Optional<CodeMapping> result = repository.findById(id);
        assertNotNull(result.get());
    }

    @Test
    public void ut1003_modify() throws Exception {
        String id = ID;
        if (!repository.existsById(id)) {
            repository.save(model(id));
        }
        Optional<CodeMapping> result = repository.findById(id);
        final CodeMapping entity = result.get();
        final Long regtime = System.currentTimeMillis();
        entity.setRegtime(regtime);
        repository.save(entity);
        Optional<CodeMapping> result2 = repository.findById(id);
        assertEquals(regtime, result2.get().getRegtime());
    }

    @Test
    public void ut1004_remove() throws Exception {
        final String id = "43013FEDD886E3C78E8727E78F2B097AE5";
        logger.info("ID: {}", id);
        Optional<CodeMapping> result = repository.findById(id);
        if (!result.isPresent()) {
            repository.save(model(id));
        }
        repository.deleteById(id);
        boolean exists = repository.existsById(id);
        assertFalse(exists);
    }

}
