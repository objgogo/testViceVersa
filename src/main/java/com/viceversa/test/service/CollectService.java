package com.viceversa.test.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.viceversa.test.vo.Response;
import com.viceversa.test.vo.Result;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicReference;

@Service
public class CollectService {

    private RestTemplate restTemplate;

    @Value("${data.api.key.encoding}")
    private String encodingKey;

    @Value("${data.api.key.decoding}")
    private String decodingKey;

    @Value("${data.api.endpoint}")
    private String endpoint;

    private final WebClient webClient;

    private final ObjectMapper objectMapper;

    public CollectService() {
        this.webClient = WebClient.create(endpoint);
        this.objectMapper = new ObjectMapper();
    }

    public String callJsonData(){

        //https://apis.data.go.kr/B551011/PhotoGalleryService1/galleryList1?serviceKey=3t7IGETT%2BssSjcJ2xZnLiSzQ9YFdKADJOqaJ%2B1fknQ30VM7sRJgEiI97aKomyXVwQlNX9uiJ6vx5Cy9BP84Hhg%3D%3D&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&arrange=A&_type=json

        //http://apis.data.go.kr/B551011/PhotoGalleryService1?serviceKey=3t7IGETT%252BssSjcJ2xZnLiSzQ9YFdKADJOqaJ%252B1fknQ30VM7sRJgEiI97aKomyXVwQlNX9uiJ6vx5Cy9BP84Hhg%253D%253D&amp;arrange=A&amp;_type=json&amp;MobileApp=AppTest&amp;MobileOS=ETC&amp;numOfRows=10&amp;pageNo=1

//        HttpHeaders headers = new HttpHeaders();
//        headers.add("accept","*/*");
//
        UriComponents uri = UriComponentsBuilder
                .fromHttpUrl(endpoint)
                .queryParam("serviceKey",encodingKey)
                .queryParam("arrange","A")
                .queryParam("_type","json")
                .queryParam("MobileApp","AppTest")
                .queryParam("MobileOS","ETC")
                .queryParam("numOfRows","10")
                .queryParam("pageNo","1").build(true);


        System.out.println(uri.toString());

        Result response =
        webClient
                .get()
//                .uri("https://apis.data.go.kr/B551011/PhotoGalleryService1/galleryList1?serviceKey=3t7IGETT+ssSjcJ2xZnLiSzQ9YFdKADJOqaJ+1fknQ30VM7sRJgEiI97aKomyXVwQlNX9uiJ6vx5Cy9BP84Hhg==&arrange=A&_type=json&MobileApp=AppTest&MobileOS=ETC&numOfRows=10&pageNo=1")
                .uri(uri.toUri())
                .header("accept", "*/*")
                .exchangeToMono( res ->{
                    if(res.statusCode() == HttpStatus.OK){
                        return res.bodyToMono(Result.class);
                    } else {
                        return Mono.empty();
                    }
                })
                .block();










        return "response";

    }
}
