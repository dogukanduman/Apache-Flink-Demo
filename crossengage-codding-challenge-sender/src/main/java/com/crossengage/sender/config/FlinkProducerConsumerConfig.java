package com.crossengage.sender.config;

import com.crossengage.sender.domain.ContactType;
import com.crossengage.sender.domain.User;
import org.apache.flink.streaming.api.collector.selector.OutputSelector;
import org.apache.flink.streaming.api.datastream.SplitStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Dogukan Duman on 11.11.2018.
 */

@Configuration
public class FlinkProducerConsumerConfig {
    @Value("${flink.kafka.address:localhost:9092}")
    private String kafkaAddress;


    @Value("${flink.kafka.group:flink-group}")
    private String kafkaGroup;


    @Value("${flink.topic:f_10002}")
    private String consumerTopic;

    /**
     * Props for consumer
     * @return
     */
    @Bean
    @Primary
    public Properties flinkConsumerProperties() {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", kafkaAddress);
        props.setProperty("group.id", kafkaGroup);
        return props;
    }


    /**
     * Consumer which consumes incoming strings from Parser Module.
     * @param props
     * @return
     */
    @Bean
    public FlinkKafkaConsumer011<User> flinkKafkaConsumer011(Properties props) {
        return new FlinkKafkaConsumer011<>(consumerTopic, new UserDeserializationSchema(), props);
    }

    /**
     * Init StreamExecutionEnvironment
     * @return StreamExecutionEnvironment
     */
    @Bean
    public StreamExecutionEnvironment streamExecutionEnvironment() {
        return StreamExecutionEnvironment.getExecutionEnvironment();
    }


    /**
     * Splitting data stream considering communication method SMS,EMAIL or BOTH.
     * @param streamExecutionEnvironment
     * @param flinkKafkaConsumer011
     * @return
     */
    @Bean
    public SplitStream<User> stringInputStream(StreamExecutionEnvironment streamExecutionEnvironment,
                                        FlinkKafkaConsumer011<User> flinkKafkaConsumer011) {

        SplitStream<User> splitMessageStream = streamExecutionEnvironment.addSource(flinkKafkaConsumer011).split((OutputSelector<User>) user -> {
            List<String> output = new ArrayList<>();
            if (user.getContactBy() == ContactType.ALL) {
                output.add(user.getContactBy().toString());
            }
            else if (user.getContactBy() == ContactType.EMAIL) {
                output.add(user.getContactBy().toString());
            }if (user.getContactBy() == ContactType.PHONE) {
                output.add(user.getContactBy().toString());
            }
            return output;
        });
        return splitMessageStream;
    }


}