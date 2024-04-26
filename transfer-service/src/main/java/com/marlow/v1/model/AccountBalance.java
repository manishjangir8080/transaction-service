package com.marlow.v1.model;

import com.marlow.v1.dto.TransactionRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.marlow.v1.dto.TransactionStatus.FAILED;
import static com.marlow.v1.dto.TransactionStatus.SUCCESS;
import static java.math.BigDecimal.ZERO;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class AccountBalance {

    private String customerId;
    private String accountNo;
    private BigDecimal amount = ZERO;
    private List<TransactionRequest> transactionRequests = new ArrayList<>();

    public AccountBalance process(TransactionRequest transactionRequest) {
        this.accountNo = transactionRequest.getAccountNo();
        if (this.amount.add(transactionRequest.getAmount()).compareTo(ZERO) >= 0) {
            transactionRequest.setTransactionStatus(SUCCESS);
            this.amount = this.amount.add(transactionRequest.getAmount());
        } else {
            transactionRequest.setTransactionStatus(FAILED);
        }
        this.transactionRequests.add(transactionRequest);
        return this;
    }
}