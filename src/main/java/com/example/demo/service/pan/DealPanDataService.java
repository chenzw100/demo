package com.example.demo.service.pan;

import com.example.demo.dao.*;
import com.example.demo.domain.SinaTinyInfoStock;
import com.example.demo.domain.table.*;
import com.example.demo.service.qt.QtService;
import com.example.demo.service.sina.SinaService;
import com.example.demo.utils.MyChineseWorkDay;
import com.example.demo.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DealPanDataService extends QtService {

    @Autowired
    StockDayRepository stockDayRepository;
    @Autowired
    StockLimitUpRepository stockLimitUpRepository;
    @Autowired
    StockDayFiveRepository stockDayFiveRepository;
    @Autowired
    StockCurrentFiveRepository stockCurrentFiveRepository;

    @Autowired
    StockLimitUpFiveRepository stockLimitUpFiveRepository;
    @Autowired
    StockSpaceHeightRepository stockSpaceHeightRepository;
    @Autowired
    StockDownRepository stockDownRepository;
    @Autowired
    SinaService sinaService;

    public void open(){
        PRICE_CACHE.clear();
        log.info("data open start");
        openDay();
        openCurrentFive();
        openDayFive();
        openDown();
        openSpace();
        log.info("data open end");
        PRICE_CACHE.clear();
    }
    public void close(){
        SINA_CACHE.clear();
        log.info("data close start");
        dayFiveStatistic();
        currentFiveStatistic();
        fiveLimitUpStatistic();

        closeCurrentFive();
        closeDayFive();
        closeDay();
        closeDown();
        closeSpace();
        log.info("data close end");
        SINA_CACHE.clear();
    }
    private void openDown(){
        List<StockDown> todayStocks = stockDownRepository.findByDayFormatOrderByOpenBidRate(MyUtils.getDayFormat());
        if(todayStocks!=null){
            for(StockDown myStock :todayStocks){
                myStock.setTodayOpenPrice(getIntCurrentPrice(myStock.getCode()));
                stockDownRepository.save(myStock);
            }
        }
        List<StockDown> yesterdayStocks = stockDownRepository.findByDayFormatOrderByOpenBidRate(MyUtils.getDayFormat(MyUtils.getYesterdayDate()));
        if(yesterdayStocks!=null){
            for(StockDown myStock :yesterdayStocks){
                myStock.setTomorrowOpenPrice(getIntCurrentPrice(myStock.getCode()));
                stockDownRepository.save(myStock);
            }
        }
    }
    private void openSpace(){
        List<StockSpaceHeight> todayStocks = stockSpaceHeightRepository.findByDayFormat(MyUtils.getDayFormat());
        if(todayStocks!=null){
            for(StockSpaceHeight myStock :todayStocks){
                myStock.setTodayOpenPrice(getIntCurrentPrice(myStock.getCode()));
                stockSpaceHeightRepository.save(myStock);
            }
        }
        List<StockSpaceHeight> yesterdayStocks = stockSpaceHeightRepository.findByDayFormat(MyUtils.getDayFormat(MyUtils.getYesterdayDate()));
        if(yesterdayStocks!=null){
            for(StockSpaceHeight myStock :yesterdayStocks){
                myStock.setTomorrowOpenPrice(getIntCurrentPrice(myStock.getCode()));
                stockSpaceHeightRepository.save(myStock);
            }
        }
    }
    private void openDay(){
        List<StockDay> todayStocks = stockDayRepository.findByDayFormatOrderByHotSort(MyUtils.getDayFormat());
        if(todayStocks!=null){
            for(StockDay myStock :todayStocks){
                myStock.setTodayOpenPrice(getIntCurrentPrice(myStock.getCode()));
                stockDayRepository.save(myStock);
            }
        }
        List<StockDay> yesterdayStocks = stockDayRepository.findByDayFormatOrderByHotSort(MyUtils.getDayFormat(MyUtils.getYesterdayDate()));
        if(yesterdayStocks!=null){
            for(StockDay myStock :yesterdayStocks){
                myStock.setTomorrowOpenPrice(getIntCurrentPrice(myStock.getCode()));
                stockDayRepository.save(myStock);
            }
        }
    }
    private void openDayFive(){
        List<StockDayFive> todayStocks = stockDayFiveRepository.findByDayFormatOrderByHotSort(MyUtils.getDayFormat());
        if(todayStocks!=null){
            for(StockDayFive myStock :todayStocks){
                myStock.setTodayOpenPrice(getIntCurrentPrice(myStock.getCode()));
                stockDayFiveRepository.save(myStock);
            }
        }
        List<StockDayFive> yesterdayStocks = stockDayFiveRepository.findByDayFormatOrderByHotSort(MyUtils.getDayFormat(MyUtils.getYesterdayDate()));
        if(yesterdayStocks!=null){
            for(StockDayFive myStock :yesterdayStocks){
                myStock.setTomorrowOpenPrice(getIntCurrentPrice(myStock.getCode()));
                stockDayFiveRepository.save(myStock);
            }
        }
    }
    private void openCurrentFive(){
        List<StockCurrentFive> todayStocks = stockCurrentFiveRepository.findByDayFormatOrderByHotSort(MyUtils.getDayFormat());
        if(todayStocks!=null){
            for(StockCurrentFive myStock :todayStocks){
                myStock.setTodayOpenPrice(getIntCurrentPrice(myStock.getCode()));
                stockCurrentFiveRepository.save(myStock);
            }
        }
        List<StockCurrentFive> yesterdayStocks = stockCurrentFiveRepository.findByDayFormatOrderByHotSort(MyUtils.getDayFormat(MyUtils.getYesterdayDate()));
        if(yesterdayStocks!=null){
            for(StockCurrentFive myStock :yesterdayStocks){
                myStock.setTomorrowOpenPrice(getIntCurrentPrice(myStock.getCode()));
                stockCurrentFiveRepository.save(myStock);
            }
        }
    }
    private void closeDown(){
        List<StockDown> todayStocks = stockDownRepository.findByDayFormatOrderByOpenBidRate(MyUtils.getDayFormat());
        if(todayStocks!=null){
            for(StockDown myStock :todayStocks){
                myStock.setTodayClosePrice(getIntCurrentPrice(myStock.getCode()));
                stockDownRepository.save(myStock);
            }
        }
        List<StockDown> yesterdayStocks = stockDownRepository.findByDayFormatOrderByOpenBidRate(MyUtils.getDayFormat(MyUtils.getYesterdayDate()));
        if(yesterdayStocks!=null){
            for(StockDown myStock :yesterdayStocks){
                myStock.setTomorrowClosePrice(getIntCurrentPrice(myStock.getCode()));
                stockDownRepository.save(myStock);
            }
        }
    }
    private void closeSpace(){
        List<StockSpaceHeight> todayStocks = stockSpaceHeightRepository.findByDayFormat(MyUtils.getDayFormat());
        if(todayStocks!=null){
            for(StockSpaceHeight myStock :todayStocks){
                myStock.setTodayClosePrice(getIntCurrentPrice(myStock.getCode()));
                stockSpaceHeightRepository.save(myStock);
            }
        }
        List<StockSpaceHeight> yesterdayStocks = stockSpaceHeightRepository.findByDayFormat(MyUtils.getDayFormat(MyUtils.getYesterdayDate()));
        if(yesterdayStocks!=null){
            for(StockSpaceHeight myStock :yesterdayStocks){
                myStock.setTomorrowClosePrice(getIntCurrentPrice(myStock.getCode()));
                stockSpaceHeightRepository.save(myStock);
            }
        }
    }

    private void closeDay(){
        List<StockDay> todayStocks = stockDayRepository.findByDayFormatOrderByHotSort(MyUtils.getDayFormat());
        if(todayStocks!=null){
            for(StockDay myStock :todayStocks){
                myStock.setTodayClosePrice(sinaService.getIntCurrentPrice(myStock.getCode()));
                stockDayRepository.save(myStock);
            }
        }
        List<StockDay> yesterdayStocks = stockDayRepository.findByDayFormatOrderByHotSort(MyUtils.getDayFormat(MyUtils.getYesterdayDate()));
        if(yesterdayStocks!=null){
            for(StockDay myStock :yesterdayStocks){
                myStock.setTomorrowClosePrice(sinaService.getIntCurrentPrice(myStock.getCode()));
                stockDayRepository.save(myStock);
            }
        }
    }
    private void closeDayFive(){
        List<StockDayFive> todayStocks = stockDayFiveRepository.findByDayFormatOrderByHotSort(MyUtils.getDayFormat());
        if(todayStocks!=null){
            for(StockDayFive myStock :todayStocks){
                myStock.setTodayClosePrice(sinaService.getIntCurrentPrice(myStock.getCode()));
                stockDayFiveRepository.save(myStock);
            }
        }
        List<StockDayFive> yesterdayStocks = stockDayFiveRepository.findByDayFormatOrderByHotSort(MyUtils.getDayFormat(MyUtils.getYesterdayDate()));
        if(yesterdayStocks!=null){
            for(StockDayFive myStock :yesterdayStocks){
                myStock.setTomorrowClosePrice(sinaService.getIntCurrentPrice(myStock.getCode()));
                stockDayFiveRepository.save(myStock);
            }
        }
    }
    private void closeCurrentFive(){
        List<StockCurrentFive> todayStocks = stockCurrentFiveRepository.findByDayFormatOrderByHotSort(MyUtils.getDayFormat());
        if(todayStocks!=null){
            for(StockCurrentFive myStock :todayStocks){
                myStock.setTodayClosePrice(sinaService.getIntCurrentPrice(myStock.getCode()));
                stockCurrentFiveRepository.save(myStock);
            }
        }
        List<StockCurrentFive> yesterdayStocks = stockCurrentFiveRepository.findByDayFormatOrderByHotSort(MyUtils.getDayFormat(MyUtils.getYesterdayDate()));
        if(yesterdayStocks!=null){
            for(StockCurrentFive myStock :yesterdayStocks){
                myStock.setTomorrowClosePrice(sinaService.getIntCurrentPrice(myStock.getCode()));
                stockCurrentFiveRepository.save(myStock);
            }
        }
    }

    private void dayFiveStatistic(){
        String end=MyUtils.getDayFormat();
        String start =MyUtils.getDayFormat(MyChineseWorkDay.preDaysWorkDay(3,MyUtils.getCurrentDate()));
        List<StockDayFive> xgbFiveUpStocks = stockDayFiveRepository.fiveStatistic(start, end);
        log.info("--->dayFiveStatistic count:"+xgbFiveUpStocks.size());
        if(xgbFiveUpStocks.size()>0){
            for (StockDayFive xgbFiveUpStock : xgbFiveUpStocks){
                SinaTinyInfoStock tinyInfoStock = sinaService.getTiny(xgbFiveUpStock.getCode());
                log.info(xgbFiveUpStock.getShowCount()+xgbFiveUpStock.getCode()+",dayFiveStatistic High:"+xgbFiveUpStock.getFiveHighPrice()+",low:"+xgbFiveUpStock.getFiveLowPrice()+"==>new High:"+tinyInfoStock.getHighPrice()+",new Low:"+tinyInfoStock.getLowPrice());
                if(tinyInfoStock.getHighPrice()>xgbFiveUpStock.getFiveHighPrice().intValue()){
                    xgbFiveUpStock.setFiveHighPrice(tinyInfoStock.getHighPrice());
                }
                if(tinyInfoStock.getLowPrice()>xgbFiveUpStock.getFiveLowPrice().intValue()){
                    xgbFiveUpStock.setFiveLowPrice(tinyInfoStock.getLowPrice());
                }
                if(xgbFiveUpStock.getTodayOpenPrice().intValue()==10){
                    xgbFiveUpStock.setTodayOpenPrice(tinyInfoStock.getOpenPrice());
                }
                stockDayFiveRepository.save(xgbFiveUpStock);
            }
        }
    }
    private void currentFiveStatistic(){
        String end=MyUtils.getDayFormat();
        String start =MyUtils.getDayFormat(MyChineseWorkDay.preDaysWorkDay(3,MyUtils.getCurrentDate()));
        List<StockCurrentFive> xgbFiveUpStocks = stockCurrentFiveRepository.fiveStatistic(start, end);
        log.info("--->currentFiveStatistic count:"+xgbFiveUpStocks.size());
        if(xgbFiveUpStocks.size()>0){
            for (StockCurrentFive xgbFiveUpStock : xgbFiveUpStocks){
                SinaTinyInfoStock tinyInfoStock = sinaService.getTiny(xgbFiveUpStock.getCode());
                log.info(xgbFiveUpStock.getShowCount()+xgbFiveUpStock.getCode()+",currentFiveStatistic  High:"+xgbFiveUpStock.getFiveHighPrice()+",low:"+xgbFiveUpStock.getFiveLowPrice()+"==>new High:"+tinyInfoStock.getHighPrice()+",new Low:"+tinyInfoStock.getLowPrice());
                if(tinyInfoStock.getHighPrice()>xgbFiveUpStock.getFiveHighPrice().intValue()){
                    xgbFiveUpStock.setFiveHighPrice(tinyInfoStock.getHighPrice());
                }
                if(tinyInfoStock.getLowPrice()>xgbFiveUpStock.getFiveLowPrice().intValue()){
                    xgbFiveUpStock.setFiveLowPrice(tinyInfoStock.getLowPrice());
                }
                if(xgbFiveUpStock.getTodayOpenPrice().intValue()==10){
                    xgbFiveUpStock.setTodayOpenPrice(tinyInfoStock.getOpenPrice());
                }
                stockCurrentFiveRepository.save(xgbFiveUpStock);
            }
        }
    }

    private void fiveLimitUpStatistic(){
        String end=MyUtils.getDayFormat();
        String start =MyUtils.getDayFormat(MyChineseWorkDay.preDaysWorkDay(3,MyUtils.getCurrentDate()));
        List<StockLimitUpFive> xgbFiveUpStocks = stockLimitUpFiveRepository.fiveStatistic(start, end);
        log.info(start+"--->fiveLimitUpStatistic count:"+xgbFiveUpStocks.size());
        if(xgbFiveUpStocks.size()>0){
            for (StockLimitUpFive xgbFiveUpStock : xgbFiveUpStocks){
                SinaTinyInfoStock tinyInfoStock = sinaService.getTiny(xgbFiveUpStock.getCode());
                log.info(xgbFiveUpStock.getShowCount()+xgbFiveUpStock.getCode()+",fiveLimitUpStatistic High:"+xgbFiveUpStock.getFiveHighPrice()+",low:"+xgbFiveUpStock.getFiveLowPrice()+"==>new High:"+tinyInfoStock.getHighPrice()+",new Low:"+tinyInfoStock.getLowPrice());
                if(tinyInfoStock.getHighPrice()>xgbFiveUpStock.getFiveHighPrice().intValue()){
                    xgbFiveUpStock.setFiveHighPrice(tinyInfoStock.getHighPrice());
                }
                if(tinyInfoStock.getLowPrice()>xgbFiveUpStock.getFiveLowPrice().intValue()){
                    xgbFiveUpStock.setFiveLowPrice(tinyInfoStock.getLowPrice());
                }
                if(xgbFiveUpStock.getTodayOpenPrice().intValue()==10){
                    xgbFiveUpStock.setTodayOpenPrice(tinyInfoStock.getOpenPrice());
                }
                stockLimitUpFiveRepository.save(xgbFiveUpStock);
            }
        }
    }

}
