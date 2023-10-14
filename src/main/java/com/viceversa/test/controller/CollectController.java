package com.viceversa.test.controller;

import com.viceversa.test.service.CollectService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/collect")
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
