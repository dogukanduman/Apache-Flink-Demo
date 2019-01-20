package com.crossengage.filereader;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FileReaderApplication implements CommandLineRunner {

    public static void main(String[] args)
    {
        SpringApplication  app = new SpringApplication(FileReaderApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args).close();
    }

    @Autowired
    StreamExecutionEnvironment streamExecutionEnvironment;

    @Override
    public void run(String[] args) throws Exception {

        streamExecutionEnvironment.execute();
    }
}

