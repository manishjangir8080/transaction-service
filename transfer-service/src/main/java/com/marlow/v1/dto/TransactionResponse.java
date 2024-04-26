package com.marlow.v1.dto;

import lombok.Builder;

@Builder
public record TransactionResponse(Meta meta, TransactionResponseData data) {
}