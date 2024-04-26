package com.marlow.v1.exceptions;

public class KafkaStreamsUnavailableException extends RuntimeException {

    public KafkaStreamsUnavailableException(String message) {
        super(message);
    }
}
