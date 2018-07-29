package module.testbase;

import org.chiwooplatform.aws.samples.config.ApplicationConfiguration;
import org.chiwooplatform.aws.samples.config.DynamoDBConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.context.annotation.Import;

@Import({ ApplicationConfiguration.class, DynamoDBConfiguration.class })
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class,
        JdbcTemplateAutoConfiguration.class })
public class DynamoDBTestApplication {

}
