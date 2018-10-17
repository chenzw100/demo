package com.example.demo.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.List;


@RestController
public class SinaController {
    private final static Log logger = LogFactory.getLog(SinaController.class);
    @Autowired
    private RestTemplate restTemplate;
    @RequestMapping("/sina")
    public String sina(){
        String url ="https://m.weibo.cn/api/container/getIndex?containerid=230945_-_Finance_Cardlist_Ranklist&page=2";
        logger.info("call test.");
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Upgrade-Insecure-Requests", "1");
        requestHeaders.add("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
        requestHeaders.add("X-DevTools-Emulate-Network-Conditions-Client-Id", "978DD05B2FC0557C900FCD244B4EAAEA");
        requestHeaders.add("Accept","application/json, text/plain, */*");
        requestHeaders.add("Referer","https://m.weibo.cn/p/tabbar?containerid=230771_-_ZUHE_INDEX");
        List<String> cookieList = new ArrayList<String>();
        cookieList.add("MLOGIN=1");


        //System.out.println("cookie为：" + cookieList.toString());
        requestHeaders.put(HttpHeaders.COOKIE,cookieList); //将cookie放入header



        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        String sttr = response.getBody();
        logger.info("sttr="+sttr);

        return sttr;
    }
    //"ALF=1542331331; _T_WM=ec471b7c93ec4e1a65494d7e2bf76c13;
    // SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WW02UHxB3vuas4BsL2eXBvL5JpX5K-hUgL.FozpS0nfeKzpe0e2dJLoIEyodcxLi--Xi-zRiKy2i--Ri-z7iK.4i--Xi-zRiK.ci--Xi-iWiKyF;
    // SUHB=0YhQW2yaEsFgb0; SSOLoginState=1539739510; MLOGIN=1"
//cookieList.add(cookie.getName() + "=" + cookie.getValue());
}
