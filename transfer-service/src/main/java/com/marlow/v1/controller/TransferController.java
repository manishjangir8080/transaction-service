package com.marlow.v1.controller;

import com.marlow.v1.dto.TransactionResponse;
import com.marlow.v1.dto.TransactionRequest;
import com.marlow.v1.service.TransferService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/transfer")
public class TransferController {

    private final TransferService transferService;

    @PostMapping
    public ResponseEntity<TransactionResponse> transact(@RequestBody TransactionRequest transactionRequest) {
        return ok(transferService.transact(transactionRequest));
    }
}