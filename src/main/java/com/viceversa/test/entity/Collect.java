package com.viceversa.test.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "collect")
@Getter
@Setter
public class Collect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDateTime createdDt;

    @Column(name = "collect_type")
    private String collectType;

    @Column(name = "item_count")
    private int itemCount;

    private String keyword;

    @OneToMany(mappedBy = "collect", targetEntity = Gallery.class, fetch = FetchType.LAZY)
    private List<Gallery> galleryList;
}
