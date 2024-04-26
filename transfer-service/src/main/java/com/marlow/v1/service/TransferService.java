package com.marlow.v1.service;

import com.marlow.v1.dto.TransactionRequest;
import com.marlow.v1.dto.TransactionResponse;
import com.marlow.v1.mapper.TransactionResponseMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

import static com.marlow.v1.constants.KafkaConstants.TRANSACTION_TOPIC;

@Service
@AllArgsConstructor
@Slf4j
public class TransferService {

    private final TransactionResponseMapper transactionResponseMapper;
    private final KafkaTemplate<String, TransactionRequest> kafkaTemplate;

    @SneakyThrows
    public TransactionResponse transact(TransactionRequest transactionRequest) {
        log.info("Publishing message to topic : {}", TRANSACTION_TOPIC);
        transactionRequest.setTime(new Date());
        kafkaTemplate.send(TRANSACTION_TOPIC, transactionRequest.getAccountNo(), transactionRequest);
        log.info("Message sent successfully!!!");
        return transactionResponseMapper.map(UUID.randomUUID().toString());
    }
}