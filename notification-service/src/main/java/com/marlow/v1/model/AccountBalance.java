package com.marlow.v1.model;

import com.marlow.v1.dto.TransactionRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.math.BigDecimal.ZERO;

@NoArgsConstructor
@Data
@AllArgsConstructor
@ToString
public class AccountBalance {

    private String customerId;
    private String accountNo;
    private BigDecimal amount = ZERO;
    private List<TransactionRequest> transactionRequests = new ArrayList<>();
}