package com.example.demo.service.tgb;

import com.example.demo.dao.*;
import com.example.demo.domain.MyTotalStock;
import com.example.demo.domain.table.*;
import com.example.demo.service.qt.QtService;
import com.example.demo.utils.MyChineseWorkDay;
import com.example.demo.utils.MyUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * Created by laikui on 2019/9/2.
 * 此乃题材挖掘利器
 */
@Component
public class TgbService extends QtService {
    @Autowired
    StockDayRepository stockDayRepository;
    @Autowired
    StockCurrentRepository stockCurrentRepository;
    @Autowired
    StockLimitUpRepository stockLimitUpRepository;

    @Autowired
    StockDayFiveRepository stockDayFiveRepository;
    @Autowired
    StockCurrentFiveRepository stockCurrentFiveRepository;

    //获取24小时的热搜数据
    public void dayDate(){
        try {
            Document doc = Jsoup.connect("https://www.taoguba.com.cn/hotPop").get();
            Elements elements = doc.getElementsByClass("tbleft");
            for(int i=10;i<20;i++){
                Element element = elements.get(i);
                Element parent =element.parent();
                Elements tds =parent.siblingElements();
                String stockName = element.text();
                String url = element.getElementsByAttribute("href").attr("href");
                int length = url.length();
                String code = url.substring(length-9,length-1);
                String currentPrice = getCurrentPrice(code);
                if(currentPrice == "-1"){
                    continue;
                }
                StockDay tgbStock = new StockDay(code,stockName,MyUtils.getCurrentDate());
                tgbStock.setYesterdayClosePrice(MyUtils.getCentBySinaPriceStr(currentPrice));
                List<StockDay> list = stockDayRepository.findByCodeAndDayFormat(code,MyUtils.getDayFormat());
                if(list!=null && list.size()>0){
                    tgbStock = list.get(0);
                }
                tgbStock.setHotSort(i - 9);
                tgbStock.setHotValue(Integer.parseInt(tds.get(2).text()));
                tgbStock.setHotSeven(Integer.parseInt(tds.get(3).text()));
                log.info("==>WORKDAY:"+code);
                List<StockLimitUp> xgbStocks = stockLimitUpRepository.findByCodeAndDayFormat(code,MyUtils.getDayFormat(MyUtils.getYesterdayDate()));
                if(xgbStocks!=null && xgbStocks.size()>0){
                    StockLimitUp xgbStock =xgbStocks.get(0);
                    tgbStock.setPlateName(xgbStock.getPlateName());
                    tgbStock.setOneFlag(xgbStock.getOpenCount());
                    tgbStock.setContinuous(xgbStock.getContinueBoardCount());
                    tgbStock.setLimitUp(1);
                }else {
                    xgbStocks =stockLimitUpRepository.findByCodeAndPlateNameIsNotNullOrderByIdDesc(code);
                    if(xgbStocks!=null && xgbStocks.size()>0){
                        tgbStock.setPlateName(xgbStocks.get(0).getPlateName());
                    }else {
                        tgbStock.setPlateName("");
                    }
                    tgbStock.setOneFlag(1);
                    tgbStock.setContinuous(0);
                    tgbStock.setLimitUp(0);
                }
                stockDayRepository.save(tgbStock);

            }
        } catch (IOException e) {
            log.error("==>WORKDAY fail "+e.getMessage());
            /*try {
                Thread.sleep(500000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            dayDate();
            log.info("==>重新执行");*/
        }
    }
    //获取实时的数据
    public void currentDate(){
        try {
            log.info("==>currentDate start:");
            Document doc = Jsoup.connect("https://www.taoguba.com.cn/hotPop").get();
            Elements elements = doc.getElementsByClass("tbleft");
            for(int i=0;i<10;i++){
                Element element = elements.get(i);
                Element parent =element.parent();
                Elements tds =parent.siblingElements();
                String stockName = element.text();
                String url = element.getElementsByAttribute("href").attr("href");
                int length = url.length();
                String code = url.substring(length-9,length-1);
                String currentPrice = getCurrentPrice(code);
                if(currentPrice == null){
                    continue;
                }
                StockCurrent currentStock = new StockCurrent(code,stockName,MyUtils.getCurrentDate());
                currentStock.setHotSort(i +1);
                currentStock.setHotValue(Integer.parseInt(tds.get(2).text()));
                currentStock.setHotSeven(Integer.parseInt(tds.get(3).text()));
                stockCurrentRepository.save(currentStock);

            }
        } catch (IOException e) {
            log.error("==>current fail "+e.getMessage());
        }
    }

    public void dayFive(){
        String end = MyUtils.getDayFormat();
        String start =MyUtils.getDayFormat(MyChineseWorkDay.preDaysWorkDay(4, MyUtils.getCurrentDate()));
        List<MyTotalStock> totalStocks =  stockDayRepository.fiveInfo(start, end);
        log.info("dayFive size:"+totalStocks.size());
        for(MyTotalStock myTotalStock : totalStocks){
            StockDayFive fiveTgbStock = new StockDayFive(myTotalStock.getCode(),myTotalStock.getName());
            fiveTgbStock.setHotSort(myTotalStock.getTotalCount());
            fiveTgbStock.setHotValue(myTotalStock.getHotValue());
            fiveTgbStock.setHotSeven(myTotalStock.getHotSeven());
            String currentPrice = getCurrentPrice(myTotalStock.getCode());
            fiveTgbStock.setYesterdayClosePrice(MyUtils.getCentBySinaPriceStr(currentPrice));
            List<StockLimitUp> xgbStocks = stockLimitUpRepository.findByCodeAndDayFormat(myTotalStock.getCode(),MyUtils.getDayFormat(MyUtils.getYesterdayDate()));
            if(xgbStocks!=null && xgbStocks.size()>0){
                StockLimitUp xgbStock =xgbStocks.get(0);
                fiveTgbStock.setPlateName(xgbStock.getPlateName());
                fiveTgbStock.setOneFlag(xgbStock.getOpenCount());
                fiveTgbStock.setContinuous(xgbStock.getContinueBoardCount());
                fiveTgbStock.setLimitUp(1);
            }else {
                xgbStocks =stockLimitUpRepository.findByCodeAndPlateNameIsNotNullOrderByIdDesc(myTotalStock.getCode());
                if(xgbStocks!=null && xgbStocks.size()>0){
                    fiveTgbStock.setPlateName(xgbStocks.get(0).getPlateName());
                }else {
                    fiveTgbStock.setPlateName("");
                }
                fiveTgbStock.setOneFlag(1);
                fiveTgbStock.setContinuous(0);
                fiveTgbStock.setLimitUp(0);
            }
            fiveTgbStock.setDayFormat(MyUtils.getDayFormat());
            StockDayFive fiveTgbStockTemp =stockDayFiveRepository.findByCodeAndDayFormat(fiveTgbStock.getCode(),MyUtils.getYesterdayDayFormat());
            if(fiveTgbStockTemp!=null){
                fiveTgbStock.setShowCount(fiveTgbStockTemp.getShowCount() + 1);
            }else {
                fiveTgbStock.setShowCount(1);
            }
            stockDayFiveRepository.save(fiveTgbStock);
        }
    }


    public void currentFive(){
        String end = MyUtils.getDayFormat();
        String start =MyUtils.getDayFormat(MyChineseWorkDay.preDaysWorkDay(4, MyUtils.getCurrentDate()));
        List<MyTotalStock> totalStocks =  stockCurrentRepository.fiveInfo(start, end);
        log.info("currentFive size:"+totalStocks.size());
        for(MyTotalStock myTotalStock : totalStocks){
            StockCurrentFive fiveTgbStock = new StockCurrentFive(myTotalStock.getCode(),myTotalStock.getName());
            fiveTgbStock.setHotSort(myTotalStock.getTotalCount());
            fiveTgbStock.setHotValue(myTotalStock.getHotValue());
            fiveTgbStock.setHotSeven(myTotalStock.getHotSeven());
            String currentPrice = getCurrentPrice(myTotalStock.getCode());
            fiveTgbStock.setYesterdayClosePrice(MyUtils.getCentBySinaPriceStr(currentPrice));
            List<StockLimitUp> xgbStocks = stockLimitUpRepository.findByCodeAndDayFormat(myTotalStock.getCode(),MyUtils.getDayFormat(MyUtils.getYesterdayDate()));
            if(xgbStocks!=null && xgbStocks.size()>0){
                StockLimitUp xgbStock =xgbStocks.get(0);
                fiveTgbStock.setPlateName(xgbStock.getPlateName());
                fiveTgbStock.setOneFlag(xgbStock.getOpenCount());
                fiveTgbStock.setContinuous(xgbStock.getContinueBoardCount());
                fiveTgbStock.setLimitUp(1);
            }else {
                xgbStocks =stockLimitUpRepository.findByCodeAndPlateNameIsNotNullOrderByIdDesc(myTotalStock.getCode());
                if(xgbStocks!=null && xgbStocks.size()>0){
                    fiveTgbStock.setPlateName(xgbStocks.get(0).getPlateName());
                }else {
                    fiveTgbStock.setPlateName("");
                }
                fiveTgbStock.setOneFlag(1);
                fiveTgbStock.setContinuous(0);
                fiveTgbStock.setLimitUp(0);
            }
            fiveTgbStock.setDayFormat(MyUtils.getDayFormat());
            StockCurrentFive fiveTgbStockTemp =stockCurrentFiveRepository.findByCodeAndDayFormat(fiveTgbStock.getCode(),MyUtils.getYesterdayDayFormat());
            if(fiveTgbStockTemp!=null){
                fiveTgbStock.setShowCount(fiveTgbStockTemp.getShowCount() + 1);
            }else {
                fiveTgbStock.setShowCount(1);
            }
            stockCurrentFiveRepository.save(fiveTgbStock);
        }
    }

}
