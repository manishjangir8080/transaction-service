package com.marlow.v1.mapper;

import com.marlow.v1.dto.TransactionResponse;
import com.marlow.v1.dto.TransactionResponseData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static com.marlow.v1.constants.TransferConstants.TRANSACTION_CODE;
import static com.marlow.v1.constants.TransferConstants.TRANSACTION_MSG;

@AllArgsConstructor
@Component
public class TransactionResponseMapper {

    private final MetaMapper metaMapper;

    public TransactionResponse map(String referenceNo) {
        return TransactionResponse.builder()
                .meta(metaMapper.map(TRANSACTION_CODE, TRANSACTION_MSG))
                .data(mapData(referenceNo))
                .build();
    }

    private TransactionResponseData mapData(String referenceNo) {
        return TransactionResponseData.builder()
                .referenceNo(referenceNo)
                .build();
    }
}