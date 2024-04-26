package com.marlow.v1.service;

import com.marlow.v1.exceptions.AccountNotFoundException;
import com.marlow.v1.exceptions.KafkaStreamsUnavailableException;
import com.marlow.v1.model.AccountBalance;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.stereotype.Service;

import static com.marlow.v1.constants.KafkaConstants.ACCOUNT_BALANCE_STORE;
import static java.util.Objects.nonNull;

@Service
@AllArgsConstructor
@Slf4j
public class AccountService {

    private final StreamsBuilderFactoryBean factoryBean;

    public AccountBalance getAccountBalance(String accountNo) {
        AccountBalance accountBalance = getStore().get(accountNo);
        if (nonNull(accountBalance)) {
            return accountBalance;
        }
        throw new AccountNotFoundException("Invalid accountNo : " + accountNo);
    }

    private ReadOnlyKeyValueStore<String, AccountBalance> getStore() {
        KafkaStreams kafkaStreams = factoryBean.getKafkaStreams();
        if (nonNull(kafkaStreams)) {
            return kafkaStreams
                    .store(StoreQueryParameters.fromNameAndType(ACCOUNT_BALANCE_STORE, QueryableStoreTypes.keyValueStore()));
        }
        throw new KafkaStreamsUnavailableException("Data is unavailable");
    }
}