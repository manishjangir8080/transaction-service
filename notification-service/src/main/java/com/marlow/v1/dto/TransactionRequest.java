package com.marlow.v1.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

import static com.marlow.v1.dto.TransactionStatus.ACCEPTED;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {

    private String customerId;

    private String accountNo;

    private BigDecimal amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy hh:mm:ss")
    public Date time;

    @Builder.Default
    private TransactionStatus transactionStatus = ACCEPTED;
}