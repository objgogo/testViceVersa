package com.viceversa.test.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Body {

    @JsonProperty("items")
    private Items items;

    @JsonProperty("numOfRows")
    private int numOfRows;

    @JsonProperty("pageNo")
    private int pageNo;

    @JsonProperty("totalCount")
    private int totalCount;


}
