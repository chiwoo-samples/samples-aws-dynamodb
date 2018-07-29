package org.chiwooplatform.aws.samples.repository.dynamodb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Optional;
import java.util.UUID;

import org.chiwooplatform.aws.samples.model.Student;
import org.chiwooplatform.aws.samples.model.StudentID;
import org.chiwooplatform.aws.samples.support.Utils;
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
public class StudentRepositoryTest {

    protected final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StudentRepository repository;

    protected Student model(StudentID id) {
        final Student m = new Student();
        m.setStdid(id.getStdid());
        m.setDeptno(id.getDeptno());
        m.setName("NAME");
        m.setGrade(Utils.random(1, 5));
        m.setRegdtm(System.currentTimeMillis());
        return m;
    }

    @Test
    public void testInstances() throws Exception {
        assertNotNull(repository);
        logger.info("repository: {}", repository);
    }

    @Test
    public void ut1001_crud() throws Exception {
        String id = UUID.randomUUID().toString();
        String num = System.currentTimeMillis() + "";
        StudentID ID = new StudentID(id, num);
        logger.info("ID: {}", id);
        Student entity = model(ID);
        repository.save(entity);
        Optional<Student> result = repository.findById(ID);
        logger.info("result: {}", result.map(v -> v).orElse(null));
        assertEquals(id, result.get().getStdid());
        repository.deleteById(ID);
        boolean exists = repository.existsById(ID);
        assertFalse(exists);
    }

    private static final String ID = "234343FEDD886E3C78E8727E78F2B097AE5";

    private static final String NUM = "20180701";

    @Test
    public void ut1002_add() throws Exception {
        StudentID id = new StudentID(ID, NUM);
        logger.info("ID: {}", id);
        Student entity = model(id);
        repository.save(entity);
        Optional<Student> result = repository.findById(id);
        assertEquals(id.toString(), result.get().getId().toString());
    }

    @Test
    public void ut1002_get() throws Exception {
        StudentID id = new StudentID(ID, NUM);
        Optional<Student> result = repository.findById(id);
        assertNotNull(result.get());
    }

    @Test
    public void ut1003_modify() throws Exception {
        StudentID id = new StudentID(ID, NUM);
        if (!repository.existsById(id)) {
            repository.save(model(id));
        }
        Optional<Student> result = repository.findById(id);
        final Student entity = result.get();
        final Long regtime = System.currentTimeMillis();
        entity.setRegdtm(regtime);
        repository.save(entity);
        Optional<Student> result2 = repository.findById(id);
        assertEquals(regtime, result2.get().getRegdtm());
    }

    @Test
    public void ut1004_remove() throws Exception {
        StudentID id = new StudentID(ID, NUM);
        Optional<Student> result = repository.findById(id);
        if (!result.isPresent()) {
            repository.save(model(id));
        }
        repository.deleteById(id);
        boolean exists = repository.existsById(id);
        assertFalse(exists);
    }

}
