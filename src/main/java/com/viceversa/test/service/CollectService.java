package com.viceversa.test.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.viceversa.test.entity.Collect;
import com.viceversa.test.entity.Gallery;
import com.viceversa.test.repository.CollectRepository;
import com.viceversa.test.repository.GalleryRepository;
import com.viceversa.test.vo.Item;
import com.viceversa.test.vo.RequestVo;
import com.viceversa.test.vo.Response;
import com.viceversa.test.vo.Result;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class CollectService {

    @Value("${data.api.key.encoding}")
    private String encodingKey;

    @Value("${data.api.key.decoding}")
    private String decodingKey;

    @Value("${data.api.endpoint}")
    private String endpoint;
    private final WebClient webClient;
    private GalleryRepository galleryRepository;
    private CollectRepository collectRepository;

    public CollectService(GalleryRepository galleryRepository, CollectRepository collectRepository) {
        this.webClient = WebClient.create(endpoint);
        this.galleryRepository = galleryRepository;
        this.collectRepository = collectRepository;
    }

    public String collectGalleryList(RequestVo req) throws JsonProcessingException {

        UriComponents uri = UriComponentsBuilder
                .fromHttpUrl(endpoint + "/galleryList1")
                .queryParam("serviceKey",encodingKey)
                .queryParam("arrange","A")
                .queryParam("_type","json")
                .queryParam("MobileApp","AppTest")
                .queryParam("MobileOS","ETC")
                .queryParam("numOfRows","50")
                .queryParam("pageNo",req.getPageNo()).build(true);

        String responseStr =
        webClient
                .get()
                .uri(uri.toUri())
                .header("accept", "*/*")
                .exchangeToMono( res ->{
                    if(res.statusCode() == HttpStatus.OK){
                        return res.bodyToMono(String.class);
                    } else {
                        return Mono.empty();
                    }
                })
                .block();

        ObjectMapper objectMapper = JsonMapper.builder()
                .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
                .build();

        Result response = objectMapper.readValue(responseStr,Result.class);

        if(response.getResponse().getBody().getTotalCount() >0){
            Item[] items = response.getResponse().getBody().getItems().getItem();

            if(items.length >0) {

                Collect collect = new Collect();
                collect.setCollectType("gallery");
                collect.setCreatedDt(LocalDateTime.now());
                collect.setItemCount(items.length);

                collect = collectRepository.save(collect);

                saveGallery(collect,items);

                return "response";
            } else {
                return "not found item";
            }
        } else {
            return "empty";
        }


    }

    public String collectGalleryDetailList(RequestVo req) throws UnsupportedEncodingException, JsonProcessingException {


        if(req.getTitle() == null || req.getTitle().isEmpty()){
            return "require title";
        }

        UriComponents uri = UriComponentsBuilder
                .fromHttpUrl(endpoint + "/galleryDetailList1")
                .queryParam("serviceKey",encodingKey)
                .queryParam("_type","json")
                .queryParam("MobileApp","AppTest")
                .queryParam("MobileOS","ETC")
                .queryParam("numOfRows","50")
                .queryParam("title", URLEncoder.encode(req.getTitle(),"UTF-8"))
                .queryParam("pageNo",req.getPageNo())
                .build(true);

        String responseStr =
                webClient
                        .get()
                        .uri(uri.toUri())
                        .header("accept", "*/*")
                        .exchangeToMono( res ->{
                            if(res.statusCode() == HttpStatus.OK){
                                return res.bodyToMono(String.class);
                            } else {
                                return Mono.empty();
                            }
                        })
                        .block();

        ObjectMapper objectMapper = JsonMapper.builder()
                .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
                .build();

        Result response = objectMapper.readValue(responseStr,Result.class);

        if(response.getResponse().getBody().getTotalCount() >0){
            Item[] items = response.getResponse().getBody().getItems().getItem();

            if(items.length > 0){

                Collect collect = new Collect();
                collect.setCollectType("gallery_detail");
                collect.setCreatedDt(LocalDateTime.now());
                collect.setItemCount(items.length);
                collect.setKeyword(req.getTitle());

                collect = collectRepository.save(collect);

                saveGallery(collect,items);

                return "success";
            } else {
                return "not found item";
            }
        } else {
            return "empty";
        }
    }

    public Page<Gallery> findGallery(RequestVo req){
        Pageable pageable = PageRequest.of(0, 10);
        Specification<Gallery> result = findGalleryByKeyword(req.getTitle());
        return galleryRepository.findAll(result,pageable);
    }


    private Specification<Gallery> findGalleryByKeyword(String keyword) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get("galSearchKeyword"), "%"+keyword+"%");
        };
    }



    // Item DB insert 함수
    private void saveGallery(Collect collect, Item[] items){

        for(Item item : items){
            Gallery gallery = new Gallery();
            gallery.setGalContentId(item.getGalContentId());
            gallery.setGalContentTypeId(item.getGalContentTypeId());
            gallery.setGalTitle(item.getGalTitle());
            gallery.setGalWebImageUrl(item.getGalWebImageUrl());
            gallery.setGalCreatedtime(item.getGalCreatedtime());
            gallery.setGalModifiedtime(item.getGalModifiedtime());
            gallery.setGalPhotographyMonth(item.getGalPhotographyMonth());
            gallery.setGalPhotographyLocation(item.getGalPhotographyLocation());
            gallery.setGalPhotographer(item.getGalPhotographer());
            gallery.setGalSearchKeyword(item.getGalSearchKeyword());
            gallery.setCollect(collect);

            galleryRepository.save(gallery);
        }
    }
}
