package com.marlow.v1.dto;

import lombok.Builder;

@Builder
public record Meta(String code, String message) {
}