package org.chiwooplatform.aws.samples.repository.dynamodb;

import org.chiwooplatform.aws.samples.model.Student;
import org.chiwooplatform.aws.samples.model.StudentID;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface StudentRepository extends CrudRepository<Student, StudentID> {

}
