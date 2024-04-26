package com.marlow.v1.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "customerId cannot be empty")
    private String customerId;

    @NotNull(message = "accountNo cannot be empty")
    private String accountNo;

    @NotNull(message = "amount cannot be empty")
    @Min(1)
    private BigDecimal amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy hh:mm:ss")
    public Date time;

    @Builder.Default
    private TransactionStatus transactionStatus = ACCEPTED;
}