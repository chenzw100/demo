package com.example.demo.controller;

import com.example.demo.utils.UtilsRequestCallBack;
import com.example.demo.utils.UtilsResponseExtractor;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloController {
    @Autowired
    private RestTemplate restTemplate;
    @RequestMapping("/sina")
    public String sina(){
        String url ="https://hq.sinajs.cn/list=sz002846";
        Object response =  restTemplate.getForObject(url,String.class);
        String str = response.toString();

        System.out.println(str);
        return "str";
    }
    @RequestMapping("/hello")
    public String hello(){
        /*CloseableHttpClient httpCilent = HttpClients.createDefault();//Creates CloseableHttpClient instance with default configuration.
        HttpGet httpGet = new HttpGet("http://www.baidu.com");
        try {
            CloseableHttpResponse response = httpCilent.execute(httpGet);
            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                httpCilent.close();//释放资源
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

        ResponseExtractor responseExtractor =  new UtilsResponseExtractor(restTemplate,String.class);
        Object response =  restTemplate.execute("https://www.taoguba.com.cn/hotPop", HttpMethod.GET,null,responseExtractor);
        String str = response.toString();
        int begin = str.indexOf("实时个股搜索热度");
        int end = str.indexOf("24小时用户搜索热度");
        str=str.substring(begin,end);
        System.out.println(str);
        return "111";
    }


    @RequestMapping("/getmy")
    public String getmy() throws IOException {
        Map resultMap = new HashMap();
        StringBuilder sb = new StringBuilder();
        Document doc = Jsoup.connect("https://www.taoguba.com.cn/hotPop").get();
        Elements elements = doc.getElementsByClass("tbleft");
        for(int i=0;i<3;i++){
            Element element = elements.get(i);
            String urlgegu = element.getElementsByAttribute("href").attr("href");
            System.out.println(element.text()+":"+urlgegu);
            //https://hq.sinajs.cn/list=sh603790

        }
        return sb.toString();
    }

    @RequestMapping("/ths")
    public String ths() throws IOException {
        //http://d.10jqka.com.cn/v2/realhead/hs_002803/last.js 69 30 10
        CloseableHttpClient httpCilent = HttpClients.createDefault();//Creates CloseableHttpClient instance with default configuration.
       String url="http://d.10jqka.com.cn/v2/realhead/hs_002803/last.js";
        try {

            HttpGet httpget = new HttpGet(url);
            httpget.addHeader("Accept", "text/html");
            httpget.addHeader("Accept-Charset", "utf-8");
            httpget.addHeader("Accept-Encoding", "gzip");
            httpget.addHeader("Accept-Language", "en-US,en");
            httpget.addHeader("User-Agent","Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.160 Safari/537.22");

            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        System.out.println(status);
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        System.out.println(status);
                        Date date=new Date();
                        System.out.println(date);
                        System.exit(0);
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }
            };

            String response = httpCilent.execute(httpget,responseHandler);
            System.out.printf(response);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                httpCilent.close();//释放资源
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "2";
    }
}
