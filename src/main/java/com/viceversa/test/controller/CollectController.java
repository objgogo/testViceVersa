package com.viceversa.test.controller;

import com.viceversa.test.service.CollectService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/collect")
@Api(tags = "[수집] 갤러리 데이터 수집 API", protocols = "http", produces = "application/json", consumes = "application/json")
public class CollectController {

    private CollectService collectService;

    public CollectController(CollectService collectService) {
        this.collectService = collectService;
    }

    @GetMapping("/galleryList1")
    public String collectData(){
        return collectService.callJsonData();
    }
}
