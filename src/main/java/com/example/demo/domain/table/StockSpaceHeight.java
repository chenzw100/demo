package com.example.demo.domain.table;

import com.example.demo.utils.MyUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity(name="stock_space_height")
public class StockSpaceHeight {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false,columnDefinition="varchar(10) COMMENT 'yyyymmdd'")
    private String dayFormat;

    @Column(nullable = true,columnDefinition="varchar(8)")
    private String code;
    @Column(nullable = true,columnDefinition="varchar(8)")
    private String name;
    @Column(nullable = true,columnDefinition="int(11) DEFAULT 0 COMMENT '开板次数'")
    private int open;
    @Column(nullable = true,columnDefinition="int(11) DEFAULT 0 COMMENT '连板'")
    private int continueCount;
    @Column(nullable = true,columnDefinition="varchar(200) COMMENT '板块'")
    private String plate;


    @Column(nullable = false,columnDefinition="int(11) DEFAULT 0 COMMENT '昨日收盘'")
    private Integer yesterdayClosePrice;

    @Column(nullable = true,columnDefinition="int(11) DEFAULT 0 COMMENT '今日开盘'")
    private Integer todayOpenPrice;
    @Column(nullable = true,columnDefinition="int(11) DEFAULT 0 COMMENT '今日收盘'")
    private Integer todayClosePrice;
    @Column(nullable = true,columnDefinition="int(11) DEFAULT 0 COMMENT '明天开盘'")
    private Integer tomorrowOpenPrice;
    @Column(nullable = true,columnDefinition="int(11) DEFAULT 0 COMMENT '明天收盘'")
    private Integer tomorrowClosePrice;
    public StockSpaceHeight(){}
    public StockSpaceHeight(String code, String name){
        this.code =code;
        this.name = name;
        this.yesterdayClosePrice=10;
        this.todayOpenPrice=10;
        this.todayClosePrice=10;
        this.tomorrowOpenPrice=10;
        this.tomorrowClosePrice=10;
    }

    public Integer getYesterdayClosePrice() {
        return yesterdayClosePrice;
    }

    public void setYesterdayClosePrice(Integer yesterdayClosePrice) {
        this.yesterdayClosePrice = yesterdayClosePrice;
    }

    public Integer getTodayOpenPrice() {
        return todayOpenPrice;
    }

    public void setTodayOpenPrice(Integer todayOpenPrice) {
        this.todayOpenPrice = todayOpenPrice;
    }

    public Integer getTodayClosePrice() {
        return todayClosePrice;
    }

    public void setTodayClosePrice(Integer todayClosePrice) {
        this.todayClosePrice = todayClosePrice;
    }

    public Integer getTomorrowOpenPrice() {
        return tomorrowOpenPrice;
    }

    public void setTomorrowOpenPrice(Integer tomorrowOpenPrice) {
        this.tomorrowOpenPrice = tomorrowOpenPrice;
    }

    public Integer getTomorrowClosePrice() {
        return tomorrowClosePrice;
    }

    public void setTomorrowClosePrice(Integer tomorrowClosePrice) {
        this.tomorrowClosePrice = tomorrowClosePrice;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDayFormat() {
        return dayFormat;
    }

    public void setDayFormat(String dayFormat) {
        this.dayFormat = dayFormat;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOpen() {
        return open;
    }

    public void setOpen(int open) {
        this.open = open;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public int getContinueCount() {
        return continueCount;
    }

    public void setContinueCount(int continueCount) {
        this.continueCount = continueCount;
    }

    public String getTodayOpenRate() {
        //(todayPrice-yesterdayPrice)/yesterdayPrice
        return MyUtils.getIncreaseRate(getTodayOpenPrice(),getYesterdayClosePrice()).toString();
    }
    public String getTodayCloseRate() {
        //(todayClosePrice-todayPrice)/todayPrice
        return MyUtils.getIncreaseRate(getTodayClosePrice(),getTodayOpenPrice()).toString();
    }

    public String getTomorrowOpenRate() {
        //(tomorrowPrice-todayPrice)/todayPrice
        return MyUtils.getIncreaseRate(getTomorrowOpenPrice(),getTodayOpenPrice()).toString();
    }

    public String getTomorrowCloseRate() {
        //(tomorrowPrice-todayPrice)/todayPrice
        return MyUtils.getIncreaseRate(getTomorrowClosePrice(),getTodayOpenPrice()).toString();
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(getCode()).append(getName()).append(",连板:").
                append(continueCount).append(",开板:").append(open)
                .append(",[竞价:").append(getTodayOpenRate()).append(",收盘:").append(getTodayCloseRate()).append(",明天:").append(getTomorrowOpenRate()).
                append(":").append(getTomorrowCloseRate()).append("]").append(plate).append("<br>");
        return sb.toString();
    }
}
