package com.example.demo.dao;

import com.example.demo.domain.table.StockCurrentFive;
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
public interface StockCurrentFiveRepository extends JpaRepository<StockCurrentFive,Long> {
    List<StockCurrentFive> findAll();
    StockCurrentFive findByCodeAndDayFormat(String code, String dayFormat);
    List<StockCurrentFive> findByDayFormatOrderByHotSort(String dayFormat);
    List<StockCurrentFive> findByDayFormatOrderByHotSevenDesc(String dayFormat);
    List<StockCurrentFive> findByDayFormatOrderByOpenBidRateDesc(String dayFormat);
    List<StockCurrentFive> findByDayFormatOrderByOpenBidRate(String dayFormat);
    StockCurrentFive save(StockCurrentFive tgbStock);
    @Query(value="SELECT * from stock_current_five WHERE day_format BETWEEN ?1 AND ?2", nativeQuery = true)
    public List<StockCurrentFive> fiveStatistic(String start, String end);


}
