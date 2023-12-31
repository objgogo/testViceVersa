package com.viceversa.test.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.viceversa.test.entity.Gallery;
import com.viceversa.test.service.CollectService;
import com.viceversa.test.vo.RequestVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/collect")
@Api(tags = "갤러리 데이터 API", protocols = "http", produces = "application/json", consumes = "application/json")
public class CollectController {

    private CollectService collectService;

    public CollectController(CollectService collectService) {
        this.collectService = collectService;
    }

    @PostMapping("/galleryList1")
    @ApiOperation(value = "공공데이터 > 관광 사진 갤러리 목록 > 저장", notes = "한번에 가져오는 데이터 개수를 50개로 고정")
    public String collectGalleryList(@RequestBody RequestVo req) throws JsonProcessingException {
        return collectService.collectGalleryList(req);
    }

    @PostMapping("/galleryDetailList1")
    @ApiOperation(value = "공공데이터 > 관광 사진 갤러리 상세 목록 >  저장", notes = "한번에 가져오는 데이터 개수를 50개로 고정")
    public String collectGalleryDetailList(@RequestBody RequestVo req) throws UnsupportedEncodingException, JsonProcessingException {
        return collectService.collectGalleryDetailList(req);
    }

    @GetMapping("/galleries")
    @ApiOperation(value = "저장된 관광 사진 갤러리 > 검색", notes = "DB에 저장된 keyword의 내용으로만 검색")
    public Page<Gallery> findGalleries(@ModelAttribute RequestVo req){
        return collectService.findGallery(req);
    }
}
