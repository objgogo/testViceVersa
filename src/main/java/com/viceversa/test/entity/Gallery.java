package com.viceversa.test.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private String galContentId;
    private String galContentTypeId;
    private String galTitle;
    private String galWebImageUrl;
    private String galCreatedtime;
    private String galModifiedtime;
    private String galPhotographyMonth;
    private String galPhotographyLocation;
    private String galPhotographer;
    private String galSearchKeyword;


    @ManyToOne(targetEntity = Collect.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "collect_id")
    @JsonIgnore
    private Collect collect;

}
