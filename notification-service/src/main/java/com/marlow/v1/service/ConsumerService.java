package com.marlow.v1.service;

import com.marlow.v1.model.AccountBalance;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class ConsumerService {

    @KafkaListener(topics = "account-balances")
    public void consumeMessage(ConsumerRecord<String, AccountBalance> consumerRecord, Acknowledgment acknowledgment) {
        log.info("AccountBalance : {}", consumerRecord.value());
        // notify the Account holder
        if (consumerRecord.value().getAmount().compareTo(BigDecimal.valueOf(100)) < 0) {
            log.info("Account Holder notified!!!");
        }
        acknowledgment.acknowledge();
    }
}
