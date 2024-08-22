package main.com.ps.udfdemo.serde;


//import clicks.ClicksProtos.ClicksOuterClass.Clicks;
import com.kafka.demo.SmartThingsProtoMessage;
import org.apache.kafka.common.serialization.Serializer;

public class ProtMessageSerializer implements Serializer<SmartThingsProtoMessage> {


    @Override
    public byte[] serialize(String s, SmartThingsProtoMessage smartThingsProtoMessage) {
        return new byte[0];
    }
}