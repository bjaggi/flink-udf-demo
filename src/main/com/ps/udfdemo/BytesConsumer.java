package main.com.ps.udfdemo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static main.com.ps.udfdemo.util.Utils.JSON_MESSAGE;
import static main.com.ps.udfdemo.util.Utils.TOPIC;

public class BytesConsumer {


    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        ObjectMapper objectMapper = new ObjectMapper();
        Properties primarySiteprops = new Properties();
        primarySiteprops.put("bootstrap.servers", "pkc-ep9mm.us-east-2.aws.confluent.cloud:9092");
        primarySiteprops.put("security.protocol","SASL_SSL");
        primarySiteprops.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"CFH5CR3SC5LXEBIE\" password=\"hazXzOSIiuAd7upp8er8pdDgZUqPJttpshEVWT5T2al1y5UOA5NnlPoSZXlDxJgy\";");
        primarySiteprops.put("sasl.mechanism", "PLAIN");


        primarySiteprops.put(ProducerConfig.ACKS_CONFIG, "all");
        primarySiteprops.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        primarySiteprops.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        primarySiteprops.put(ConsumerConfig.GROUP_ID_CONFIG , "jaggi_test1");
        primarySiteprops.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG , "earliest");

        KafkaConsumer<byte[], byte[]> bytesBytesKafkaConsumer = new KafkaConsumer<byte[], byte[]>( primarySiteprops);

        //ProducerRecord<byte[], byte[]> byteRecord = new ProducerRecord<>("sm_iot_bytes", arr);
        bytesBytesKafkaConsumer.subscribe(Arrays.asList(TOPIC));
        while(true) {
            ConsumerRecords<byte[], byte[]> consumerRecords = bytesBytesKafkaConsumer.poll(100000L);
            for ( ConsumerRecord<byte[], byte[]> consumerRecord : consumerRecords){
                byte[] arr = consumerRecord.value();
                JsonNode node = objectMapper.readTree(arr);
                System.out.println(node);
                String value = node.get("components").get("main").get("switchLevel").get("level").get("value").asText();

            }

        }






    }
}