package com.pm.analyticsservice;

import com.pm.analyticsservice.kafka.KafkaConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AnalyticsServiceApplication {

    public static void main(String[] args) {
       var context = SpringApplication.run(AnalyticsServiceApplication.class, args);

      var bean = context.getBean(KafkaConsumer.class);

    }

}
