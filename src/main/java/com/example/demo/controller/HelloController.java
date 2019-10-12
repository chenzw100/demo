package com.example.demo.controller;

import com.example.demo.dao.*;
import com.example.demo.domain.table.*;
import com.example.demo.task.PanService;
import com.example.demo.utils.ChineseWorkDay;
import com.example.demo.utils.MyChineseWorkDay;
import com.example.demo.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class HelloController {
    private static String PRE_END="";
    @Autowired
    StockCurrentFiveRepository stockCurrentFiveRepository;
    @Autowired
    StockDayFiveRepository stockDayFiveRepository;
    @Autowired
    StockTemperatureRepository stockTemperatureRepository;
    @Autowired
    StockDayRepository stockDayRepository;
    @Autowired
    StockLimitUpRepository stockLimitUpRepository;
    @Autowired
    StockSpaceHeightRepository stockSpaceHeightRepository;
    @Autowired
    StockLimitUpFiveRepository stockLimitUpFiveRepository;

    @Autowired
    PanService panService;
    @RequestMapping("/hello")
    public String hello(){
        panService.currentPan();
        return "hello";
    }
    @RequestMapping("/close")
    public String close(){
        panService.closePan();
        return "close success";
    }

    @RequestMapping("/info/{end}")
    String info(@PathVariable("end")String end) {
        String queryEnd = end;
        if("1".equals(end)){
            if(isWorkday()){
                queryEnd= MyUtils.getDayFormat();
            }else {
                queryEnd=MyUtils.getYesterdayDayFormat();
            }
        }else if("2".equals(end)){
            Date endDate =  MyUtils.getFormatDate(PRE_END);
            queryEnd =MyUtils.getDayFormat(MyChineseWorkDay.preWorkDay(endDate));
        }else if("3".equals(end)){
            Date endDate =  MyUtils.getFormatDate(PRE_END);
            queryEnd =MyUtils.getDayFormat(MyChineseWorkDay.nextWorkDay(endDate));
        }
        Date endDate =  MyUtils.getFormatDate(queryEnd);
        PRE_END=queryEnd;
        String desc ="坚持模式！！<br>查询日期";
        String start =MyUtils.getDayFormat(MyChineseWorkDay.preDaysWorkDay(5,endDate));
        endDate =  MyUtils.getFormatDate(queryEnd);
        String yesterday =MyUtils.getDayFormat(MyChineseWorkDay.preDaysWorkDay(1,endDate));
        List<StockDayFive> hotSortFive = stockDayFiveRepository.findByDayFormatOrderByOpenBidRate(queryEnd);
        List<StockCurrentFive> myTgbStockFive = stockCurrentFiveRepository.findByDayFormatOrderByOpenBidRate(queryEnd);
        List<StockDay> tgbHots = stockDayRepository.findByDayFormatOrderByOpenBidRate(queryEnd);
        List<StockLimitUpFive> upFives =stockLimitUpFiveRepository.findByDayFormatOrderByContinueBoardCountDesc(queryEnd);
        List<StockTemperature> temperaturesClose=stockTemperatureRepository.close(start,queryEnd);
        List<StockSpaceHeight> hs=stockSpaceHeightRepository.findByDayFormat(yesterday);
        StockSpaceHeight hstock=null;
        if(hs!=null && hs.size()>0){
            hstock = hs.get(0);
        }
        return desc+queryEnd+"<br>最近5天市场情况<br>"+temperaturesClose+"<br>市场（新题材）最高版:"+hstock+"<br>【相信数据】股吧数量:"+hotSortFive.size()+"<br>"+hotSortFive+"end"+queryEnd+"<br>【信号 相信数据】实时数量:"+myTgbStockFive.size()+"<br>"+myTgbStockFive+queryEnd+"<br>股吧热门:<br>"+tgbHots+"<br>5版近期:<br>"+upFives;
    }
    public boolean isWorkday(){
        ChineseWorkDay chineseWorkDay = new ChineseWorkDay(MyUtils.getCurrentDate());
        try {
            if(chineseWorkDay.isWorkday()){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
