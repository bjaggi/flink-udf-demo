package main.com.ps.udfdemo.serde;


import com.kafka.demo.SmartThingsProtoMessage;
import org.apache.kafka.common.serialization.Deserializer;


public class ProtMessageDeSerializer implements Deserializer<SmartThingsProtoMessage> {
    @Override
    public SmartThingsProtoMessage deserialize(String s, byte[] bytes) {
        return null;
    }
}