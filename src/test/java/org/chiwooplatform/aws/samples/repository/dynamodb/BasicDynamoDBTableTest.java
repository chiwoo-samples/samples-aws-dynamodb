package org.chiwooplatform.aws.samples.repository.dynamodb;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.chiwooplatform.aws.samples.model.CodeMapping;
import org.chiwooplatform.aws.samples.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.ListTablesRequest;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;

import module.testbase.DynamoDBTestApplication;

@SpringJUnitConfig
@SpringBootTest(classes = DynamoDBTestApplication.class, webEnvironment = WebEnvironment.NONE)
public class BasicDynamoDBTableTest {

    protected final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CodeMappingRepository repository;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    private DynamoDBMapper dynamoDBMapper;

    @BeforeEach
    public void beforeEach() {
        dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
    }

    @Test
    public void testInstances() throws Exception {
        assertNotNull(dynamoDBMapper);
        assertNotNull(amazonDynamoDB);
        assertNotNull(repository);
        logger.info("amazonDynamoDB: {}", amazonDynamoDB);
        logger.info("repository: {}", repository);
    }

    /**
     * {@code aws dynamodb create-table --table-name Customer 
     *    --attribute-definitions AttributeName=Id,AttributeType=S 
     *    --key-schema AttributeName=Id,KeyType=HASH 
     *    --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1 
     *    --endpoint-url http://localhost:8000}
     * 
     * @throws Exception
     */
    @Test
    public void creatTablesIfNotExists() throws Exception {

        final String infcodemapptname = "inf-code-mapp";
        boolean existsinfcodemapptable = false;
        final String studenttname = "student";
        boolean existsstudenttable = false;

        ListTablesRequest lreq = new ListTablesRequest();
        ListTablesResult result = amazonDynamoDB.listTables(lreq);
        List<String> tables = result.getTableNames();
        for (final String v : tables) {
            logger.info("tname: {}", v);
            if (v.equals(infcodemapptname)) {
                existsinfcodemapptable = true;
            }
            if (v.equalsIgnoreCase(studenttname)) {
                existsstudenttable = true;
            }
        }

        if (!existsinfcodemapptable) {
            CreateTableRequest createTableRequest = dynamoDBMapper.generateCreateTableRequest(CodeMapping.class);
            createTableRequest.setProvisionedThroughput(new ProvisionedThroughput(5L, 5L));
            CreateTableResult tresult = amazonDynamoDB.createTable(createTableRequest);
            logger.info("tresult.getTableDescription(): {}", tresult.getTableDescription());
        }

        if (!existsstudenttable) {
            CreateTableRequest createTableRequest = dynamoDBMapper.generateCreateTableRequest(Student.class);
            createTableRequest.setProvisionedThroughput(new ProvisionedThroughput(5L, 5L));
            CreateTableResult tresult = amazonDynamoDB.createTable(createTableRequest);
            logger.info("tresult.getTableDescription(): {}", tresult.getTableDescription());
        }

    }

}
