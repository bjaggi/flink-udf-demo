

###
This UDF is dsigned to convert bytes to protobuf.   

Step1> Create a UDF to convert bytes t ROW<id, timestamp>   
Step2> Convert bytes to flink ROW using the FLink SQL   
Step3> Create a Flink table in Protobuf format     
Step4> Insert data from  raw table to the probuf table   

   
### Step1
```java
    @DataTypeHint("ROW<id INT, ts TIMESTAMP_LTZ(3)>")
    public Row eval(byte[] byteArr) {

        JsonNode node = null;
        try {
            node = objectMapper.readTree(byteArr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String value = node.get("components").get("main").get("switchLevel").get("level").get("value").asText();
        SmartThingsProtoMessage.ProtMessage message = SmartThingsProtoMessage.ProtMessage.newBuilder().setId(Integer.valueOf(value)).build();
        //return message.toByteArray().toString();
        return Row.of(message.getId(), Instant.ofEpochMilli(System.currentTimeMillis()));
    }

```


```shell
mvn package
```

```shell
confluent flink artifact create java-udf-artifact  --artifact-file  /Users/bjaggi/confluent/flink-udf-demo/target/flink-udf-demo-1.0-SNAPSHOT-jar-with-dependencies.jar
+----------------+-------------------+
| Name           | java-udf-artifact |
| Plugin ID      | ccp-ld1wwo        |
| Version ID     | ver-em9807        |
| Content Format | JAR               |
+----------------+-------------------+
```

```shell
confluent flink artifact describe  ccp-ld1wwo                                                    ✔  base   01:03:50 PM 
+----------------+-------------------+
| Name           | java-udf-artifact |
| Plugin ID      | ccp-ld1wwo        |
| Version ID     | ver-em9807        |
| Content Format | JAR               |
+----------------+-------------------+
```



### Step2
```sql
CREATE FUNCTION  SmBytesToFlinkRow
AS 'main.com.ps.udfdemo.SMBytesToSMProto'
USING JAR 'confluent-artifact://ccp-ld1wwo/ver-em9807';
```




### Step3
```sql
create table sm_proto_table (
value int,
ts TIMESTAMP_LTZ(3)
) WITH
( 'value.format'='proto-register');
```

```sql
insert into sm_proto_table select  EXPR$0.id as id, EXPR$0.ts  from (select SmBytesToFlinkRow(val)  from sm_iot_bytes);
```


```sql
select * from sm_proto_table;
```
