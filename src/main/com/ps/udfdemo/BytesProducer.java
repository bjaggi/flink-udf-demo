package main.com.ps.udfdemo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static main.com.ps.udfdemo.util.Utils.JSON_MESSAGE;
import static main.com.ps.udfdemo.util.Utils.TOPIC;

public class BytesProducer {


    public static void main(String[] args) throws JsonProcessingException, ExecutionException, InterruptedException {
        ObjectMapper objectMapper = new ObjectMapper();
        Properties primarySiteprops = new Properties();
        primarySiteprops.put("bootstrap.servers", "pkc-ep9mm.us-east-2.aws.confluent.cloud:9092");
        primarySiteprops.put("security.protocol","SASL_SSL");
        primarySiteprops.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"CFH5CR3SC5LXEBIE\" password=\"hazXzOSIiuAd7upp8er8pdDgZUqPJttpshEVWT5T2al1y5UOA5NnlPoSZXlDxJgy\";");
        primarySiteprops.put("sasl.mechanism", "PLAIN");

        primarySiteprops.put(ProducerConfig.ACKS_CONFIG, "all");
        primarySiteprops.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArraySerializer");
        primarySiteprops.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArraySerializer");

        KafkaProducer<byte[], byte[]> bytesBytesKafkaProducer = new KafkaProducer<byte[], byte[]>( primarySiteprops);
        JsonNode node = objectMapper.readTree(JSON_MESSAGE);
        byte[] arr = objectMapper.writeValueAsBytes(node);
        System.out.println(arr);

        ProducerRecord<byte[], byte[]> byteRecord = new ProducerRecord<>(TOPIC , arr);
        bytesBytesKafkaProducer.send( byteRecord).get();



        System.out.println(node);


    }
}