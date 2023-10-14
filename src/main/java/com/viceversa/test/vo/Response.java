package com.viceversa.test.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {

    @JsonProperty("header")
    private Header header;

    @JsonProperty("body")
    private Body body;
}
