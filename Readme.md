

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




```sql
CREATE FUNCTION  SmBytesToFlinkRow
AS 'main.com.ps.udfdemo.SMBytesToSMProto'
USING JAR 'confluent-artifact://ccp-ld1wwo/ver-em9807';
```

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
