package com.crossengage.filereader.config;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Dogukan Duman on 11.11.2018.
 */

@Configuration
public class FlinkProducerConfig {

    @Value("${file.path:test_user_data.txt")
    private String path;

    @Value("${flink.kafka.address:localhost:9092}")
    private String kafkaAddress;

    @Value("${flink.topic:f_10001}")
    private String produceTopic;

    /**
     * Producer, which produce read lines from file.
     * @return FlinkKafkaProducer011
     */
    @Bean
    FlinkKafkaProducer011<String> flinkKafkaProducer011(){
        return new FlinkKafkaProducer011<>(kafkaAddress, produceTopic, new SimpleStringSchema());
    }

    /**
     * Init StreamExecutionEnvironment
     * @return StreamExecutionEnvironment
     */
    @Bean
    StreamExecutionEnvironment streamExecutionEnvironment(){
        return StreamExecutionEnvironment.getExecutionEnvironment();
    }

    /**
     * Init Datastream with source and sink
     * @param streamExecutionEnvironment
     * @param flinkKafkaProducer011 as sink
     * @return
     */
    @Bean
    DataStream<String> stringInputStream( StreamExecutionEnvironment streamExecutionEnvironment, FlinkKafkaProducer011<String> flinkKafkaProducer011){

        DataStream<String> messageStream=streamExecutionEnvironment.readTextFile(path);
        messageStream.addSink(flinkKafkaProducer011);
        return messageStream;
    }

}
