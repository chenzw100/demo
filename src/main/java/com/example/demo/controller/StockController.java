package com.example.demo.controller;

import com.example.demo.service.StockService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

@RestController
public class StockController {
    @Autowired
    private StockService stockService;

    @RequestMapping("/taoguba")
    public String taoguba() throws IOException {
        return stockService.taoguba();
    }
}
