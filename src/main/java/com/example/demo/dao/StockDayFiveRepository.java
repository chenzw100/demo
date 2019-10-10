package com.example.demo.dao;

import com.example.demo.domain.MyFiveTgbStock;
import com.example.demo.domain.table.StockCurrentFive;
import com.example.demo.domain.table.StockDayFive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by czw on 2018/10/19.
 * JpaRepository default method
 * User user=new User();
 userRepository.findAll();
 userRepository.findOne(1l);
 userRepository.save(user);
 userRepository.delete(user);
 userRepository.count();
 userRepository.exists(1l);
 */
public interface StockDayFiveRepository extends JpaRepository<StockDayFive,Long> {
    List<StockDayFive> findAll();
    StockDayFive findByCodeAndDayFormat(String code, String dayFormat);
    List<StockDayFive> findByDayFormatOrderByHotSort(String dayFormat);
    List<StockDayFive> findByDayFormatOrderByHotSevenDesc(String dayFormat);
    List<StockDayFive> findByDayFormatOrderByOpenBidRateDesc(String dayFormat);
    List<StockDayFive> findByDayFormatOrderByOpenBidRate(String dayFormat);
    StockDayFive save(StockDayFive tgbStock);
    @Query(value="SELECT * from stock_day_five WHERE day_format BETWEEN ?1 AND ?2", nativeQuery = true)
    public List<StockDayFive> fiveStatistic(String start, String end);


}
