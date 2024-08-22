package main.com.ps.udfdemo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.demo.SmartThingsProtoMessage;
import org.apache.flink.table.annotation.DataTypeHint;
import org.apache.flink.table.functions.ScalarFunction;
import org.apache.flink.table.types.logical.RowType;
import org.apache.flink.types.Row;

import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/** TShirt sizing function for demo. */
public class SMBytesToSMProto extends ScalarFunction {
   public static final String NAME = "IS_SMALLER";
   private static final ObjectMapper objectMapper = new ObjectMapper();

    // define a nested data type
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

    public SmartThingsProtoMessage.ProtMessage eval2(byte[] byteArr) {
        JsonNode node = null;
        try {
            node = objectMapper.readTree(byteArr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String value = node.get("components").get("main").get("switchLevel").get("level").get("value").asText();
        SmartThingsProtoMessage.ProtMessage message = SmartThingsProtoMessage.ProtMessage.newBuilder().setId(Integer.valueOf(value)).build();
        return message;

    }


}