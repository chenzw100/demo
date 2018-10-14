package com.example.demo.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.lang.reflect.Type;

public class UtilsResponseExtractor implements ResponseExtractor {
    private HttpMessageConverterExtractor<String> delegate;
    private Class type;

    public UtilsResponseExtractor(RestTemplate restTemplate){
        Type responseType = String.class;
        if (responseType != null && Void.class != responseType) {
            this.delegate = new HttpMessageConverterExtractor(responseType, restTemplate.getMessageConverters());
        } else {
            this.delegate = null;
        }
    }

    public UtilsResponseExtractor(RestTemplate restTemplate, Class responseType){
        this.type = responseType;
        Type rt = String.class;
        if (rt != null && Void.class != rt) {
            this.delegate = new HttpMessageConverterExtractor(rt, restTemplate.getMessageConverters());
        } else {
            this.delegate = null;
        }
    }
    @Override
    public Object extractData(ClientHttpResponse clientHttpResponse) throws IOException {
        if (this.delegate != null) {
            Object body = this.delegate.extractData(clientHttpResponse);

            if(type != null && !type.getName().equalsIgnoreCase("java.lang.String")){
                body = new ObjectMapper().readValue((String)body, type);
            }
            return new ResponseEntity(body, clientHttpResponse.getHeaders(), clientHttpResponse.getStatusCode());
        } else {
            return new ResponseEntity(clientHttpResponse.getHeaders(), clientHttpResponse.getStatusCode());
        }
    }
}
