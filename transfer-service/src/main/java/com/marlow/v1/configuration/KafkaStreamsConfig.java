package com.marlow.v1.configuration;

import com.marlow.v1.dto.TransactionRequest;
import com.marlow.v1.model.AccountBalance;
import com.marlow.v1.reader.PropertyReader;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.HashMap;
import java.util.Map;

import static com.marlow.v1.constants.KafkaConstants.ACCOUNT_BALANCES_TOPIC;
import static com.marlow.v1.constants.KafkaConstants.ACCOUNT_BALANCE_STORE;
import static com.marlow.v1.constants.KafkaConstants.TRANSACTION_TOPIC;
import static org.apache.kafka.streams.StreamsConfig.APPLICATION_ID_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.STATE_DIR_CONFIG;
import static org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME;

@Configuration
@EnableKafka
@EnableKafkaStreams
@Slf4j
@AllArgsConstructor
public class KafkaStreamsConfig {

    private final PropertyReader propertyReader;

    @Bean(name = DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kafkaStreamsConfiguration() {
        Map<String, Object> props = new HashMap<>();
        props.put(APPLICATION_ID_CONFIG, propertyReader.getSpringApplicationName());
        props.put(BOOTSTRAP_SERVERS_CONFIG, propertyReader.getKafkaBootstrapServers());
        props.put(DEFAULT_KEY_SERDE_CLASS_CONFIG, StringDeserializer.class);
        props.put(DEFAULT_VALUE_SERDE_CLASS_CONFIG, JsonDeserializer.class);
        props.put(STATE_DIR_CONFIG, propertyReader.getKafkaStateDir());

        return new KafkaStreamsConfiguration(props);
    }

    @Bean
    public StreamsBuilder buildPipeline(StreamsBuilder streamsBuilder) {
        // Kafka Serializer for TransactionRequest
        Serde<TransactionRequest> transactionRequestSerdes = new JsonSerde<>(TransactionRequest.class);
        // Kafka Serializer for AccountBalance
        Serde<AccountBalance> accountBalanceSerde = new JsonSerde<>(AccountBalance.class);
        // KStream to process Transactions
        KStream<String, AccountBalance> accountBalancesStream = streamsBuilder.stream(TRANSACTION_TOPIC,
                        Consumed.with(Serdes.String(), transactionRequestSerdes))
                // Grouping transactions by accountNo(Key)
                .groupByKey()
                // Aggregating all transactions to derive AccountBalance at current state
                .aggregate(AccountBalance::new,
                        (key, value, aggregate) -> aggregate.process(value),
                        Materialized.<String, AccountBalance, KeyValueStore<Bytes, byte[]>>
                                        as(ACCOUNT_BALANCE_STORE)
                                .withKeySerde(Serdes.String())
                                .withValueSerde(accountBalanceSerde))
                .toStream();
        // Publish AccountBalance for notification
        accountBalancesStream
                .to(ACCOUNT_BALANCES_TOPIC, Produced.with(Serdes.String(), accountBalanceSerde));
        return streamsBuilder;
    }
}