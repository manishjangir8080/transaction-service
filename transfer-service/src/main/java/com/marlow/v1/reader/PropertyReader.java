package com.marlow.v1.reader;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class PropertyReader {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String kafkaBootstrapServers;

    @Value("${spring.kafka.state.dir}")
    private String kafkaStateDir;

    @Value("${spring.application.name}")
    private String springApplicationName;
}
