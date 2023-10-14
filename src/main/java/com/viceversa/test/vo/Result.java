package com.viceversa.test.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result {

    @JsonProperty("response")
    private Response response;
}
