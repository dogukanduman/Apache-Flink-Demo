package com.crossengage.parser.config;

import com.crossengage.parser.domain.ContactType;
import com.crossengage.parser.domain.User;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Optional;
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
    private String produceTopic;


    @Value("${flink.topic:f_10001}")
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
     * Consumer which consumes incoming strings from FileReader Module.
     * @param props
     * @return
     */
    @Bean
    FlinkKafkaConsumer011<String> flinkKafkaConsumer011(Properties props) {
        return new FlinkKafkaConsumer011<>(consumerTopic, new SimpleStringSchema(), props);
    }

    /**
     * Init StreamExecutionEnvironment
     * @return StreamExecutionEnvironment
     */
    @Bean
    StreamExecutionEnvironment streamExecutionEnvironment() {
        return StreamExecutionEnvironment.getExecutionEnvironment();
    }

    /**
     * Producer, which produce users which already mapped
     * @return FlinkKafkaProducer011
     */
    @Bean
    FlinkKafkaProducer011<User> flinkKafkaProducer011() {
        return new FlinkKafkaProducer011<>(kafkaAddress, produceTopic, new UserSerializationSchema());
    }

    /**
     * DataStream pipeline
     * @param streamExecutionEnvironment{@link {@link StreamExecutionEnvironment}}
     * @param flinkKafkaConsumer011 @link {@link FlinkKafkaConsumer011}}
     * @param flinkKafkaProducer011 @link {@link FlinkKafkaProducer011}}
     * @param userMapper responsible for mapping string to users
     * @param userFilter responsible for filtering users who will not be not notified.
     * @return
     */
    @Bean
    DataStream<String> stringInputStream(StreamExecutionEnvironment streamExecutionEnvironment,
                                         FlinkKafkaConsumer011<String> flinkKafkaConsumer011,
                                         FlinkKafkaProducer011<User> flinkKafkaProducer011,
                                         MapFunction<String, User> userMapper,
                                         FilterFunction<User> userFilter) {

        DataStream<String> messageStream = streamExecutionEnvironment.addSource(flinkKafkaConsumer011);

        messageStream.map(userMapper).filter(userFilter).addSink(flinkKafkaProducer011);

        return messageStream;
    }

    /**
     * MapperFunction for mapping strings
     * @return
     */
    @Bean
    MapFunction<String, User> userMapper() {
        return (MapFunction<String, User>) value -> {

            Optional<User> user = User.buildWith(value);

            if (user.isPresent()) {
                System.out.println("### Raw Data:["+value+"] mapped to: "+user.get());
                return user.get();
            }
            System.out.println("Raw Data:["+value+"] can not mapped will be ignore");
            return null;
        };
    }
    /**
     * FilterFunction for filtering users.
     * @return
     */
    @Bean
    FilterFunction<User> userFilter() {
        return (FilterFunction<User>) user -> {

            if(user==null){
                return false;
            }
            if(!user.getActive()){
                System.out.println("User:"+user.getId()+" does not want to notify");
                return false;
            }
            if(user.getContactBy()== ContactType.NONE){
                System.out.println("User:"+user.getId()+" is active but there is no defined contact type, will not inform.");
                return false;
            }

            return true;
        };
    }
}