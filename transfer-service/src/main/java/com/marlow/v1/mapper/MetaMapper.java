package com.marlow.v1.mapper;

import com.marlow.v1.dto.Meta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MetaMapper {

    public Meta map(String code, String message) {
        return Meta.builder()
                .code(code)
                .message(message)
                .build();
    }
}
