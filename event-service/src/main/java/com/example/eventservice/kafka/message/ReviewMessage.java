package com.example.eventservice.kafka.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
public class ReviewMessage extends BaseMessage {

    @JsonIgnore
    private List<String> storedImageUrl;

}
