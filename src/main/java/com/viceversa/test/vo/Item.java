package com.viceversa.test.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {

    @JsonProperty("galContentId")
    private String galContentId;

    @JsonProperty("galContentTypeId")
    private String galContentTypeId;

    @JsonProperty("galTitle")
    private String galTitle;

    @JsonProperty("galWebImageUrl")
    private String galWebImageUrl;

    @JsonProperty("galCreatedtime")
    private String galCreatedtime;

    @JsonProperty("galModifiedtime")
    private String galModifiedtime;

    @JsonProperty("galPhotographyMonth")
    private String galPhotographyMonth;

    @JsonProperty("galPhotographyLocation")
    private String galPhotographyLocation;

    @JsonProperty("galPhotographer")
    private String galPhotographer;

    @JsonProperty("galSearchKeyword")
    private String galSearchKeyword;

}
