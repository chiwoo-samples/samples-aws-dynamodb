package org.chiwooplatform.aws.samples.repository.dynamodb;

import org.chiwooplatform.aws.samples.model.CodeMapping;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface CodeMappingRepository extends CrudRepository<CodeMapping, String> {

}
