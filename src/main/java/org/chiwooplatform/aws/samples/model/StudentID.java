package org.chiwooplatform.aws.samples.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;

import lombok.ToString;

/**
 * <a href="https://aws.amazon.com/ko/blogs/database/choosing-the-right-dynamodb-partition-key/">Choosing the Right DynamoDB Partition
 * Key</a>
 *
 */
@ToString
public class StudentID {

    @DynamoDBHashKey
    private String stdid;

    @DynamoDBRangeKey
    private String deptno;

    public StudentID() {
        super();
    }

    public StudentID(String stdid, String deptno) {
        super();
        this.stdid = stdid;
        this.deptno = deptno;
    }

    public String getStdid() {
        return stdid;
    }

    public void setStdid(String stdid) {
        this.stdid = stdid;
    }

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno;
    }

}
