package com.example.demo.domain.table;

import com.example.demo.utils.MyUtils;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

public class StockTiny {

    private Long id;
    private String dayFormat;
    private String code;
    private String name;
    public StockTiny(String code, String name, Date date){
        this.code=code;
        this.name = name;
        this.dayFormat = MyUtils.getDayFormat(date);

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
}
