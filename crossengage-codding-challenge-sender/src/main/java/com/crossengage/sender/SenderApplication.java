package com.crossengage.sender;

import com.crossengage.sender.domain.ContactType;
import com.crossengage.sender.domain.User;
import org.apache.flink.streaming.api.datastream.SplitStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SenderApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SenderApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args).close();
    }

    @Autowired
    private StreamExecutionEnvironment streamExecutionEnvironment;

    @Autowired
    private SplitStream<User> stringInputStream;

    @Autowired
    private  RichSinkFunction<User> richSinkFunction;

    @Override
    public void run(String[] args) throws Exception {


        stringInputStream.select(ContactType.EMAIL.toString()).addSink(richSinkFunction);
        stringInputStream.select(ContactType.ALL.toString()).addSink(richSinkFunction);
        stringInputStream.select(ContactType.PHONE.toString()).addSink(richSinkFunction);
        streamExecutionEnvironment.execute();
    }
}