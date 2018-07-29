package org.chiwooplatform.aws.samples.model;

import org.springframework.data.annotation.Id;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.ToString;

/**
 * Student (key[stdid,deptno])
 * <pre><code>
{ 
  "stdid": "b1d710e1272641abafb26a6321462a2d", 
  "deptno": "abcd", 
  "name": "kano", 
  "grade": 2, 
  "regdtm": 1531201737841
}</code></pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@DynamoDBTable(tableName = "student")
@ToString
public class Student {

    @Id
    private StudentID id;
    private String name;
    private Integer grade;
    private Long regdtm;

    @DynamoDBIgnore
    public StudentID getId() {
        return id;
    }

    @DynamoDBHashKey
    public String getStdid() {
        return id.getStdid();
    }

    public void setStdid(String stdid) {
        if (id == null) {
            id = new StudentID();
        }
        id.setStdid(stdid);
    }

    @DynamoDBRangeKey
    public String getDeptno() {
        return id.getDeptno();
    }

    public void setDeptno(String deptno) {
        if (id == null) {
            id = new StudentID();
        }
        id.setDeptno(deptno);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Long getRegdtm() {
        return regdtm;
    }

    public void setRegdtm(Long regdtm) {
        this.regdtm = regdtm;
    }

}
