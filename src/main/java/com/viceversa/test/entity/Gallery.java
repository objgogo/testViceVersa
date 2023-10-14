package com.viceversa.test.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Table(name = "gallery")
@Entity
@Getter
@Setter
public class Gallery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    private long galContentId;
    private long galContentTypeId;
    private String galTitle;
    private String galWebImageUrl;
    private String galCreatedtime;
    private String galModifiedtime;
    private String galPhotographyMonth;
    private String galPhotographyLocation;
    private String galPhotographer;
    private String galSearchKeyword;
}
