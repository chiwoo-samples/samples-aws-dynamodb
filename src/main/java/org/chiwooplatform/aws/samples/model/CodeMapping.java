package org.chiwooplatform.aws.samples.model;

import org.springframework.data.annotation.Id;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.ToString;

/**
 * CodeMapping (id==ifid)
 * <pre><code>
{ 
  "id": "b1d710e1272641abafb26a6321462a2d", 
  "name": "abcd", 
  "ifid": "2050f33a110e4bd895ad03869f28ce204c17986cb16845f8bf9d8d17d764954b", 
  "regtime": 1531201737841
}</code></pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@DynamoDBTable(tableName = "inf-code-mapp")
@ToString
public class CodeMapping {

    @Id
    @DynamoDBHashKey(attributeName = "id")
    private String id;
    private String name;
    private String ifid;
    private Long regtime;

    public CodeMapping() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIfid() {
        return ifid;
    }

    public void setIfid(String ifid) {
        this.ifid = ifid;
    }

    public Long getRegtime() {
        return regtime;
    }

    public void setRegtime(Long regtime) {
        this.regtime = regtime;
    }

}
