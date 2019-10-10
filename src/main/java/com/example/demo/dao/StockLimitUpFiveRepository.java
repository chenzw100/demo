package com.example.demo.dao;

import com.example.demo.domain.table.StockLimitUpFive;
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
public interface StockLimitUpFiveRepository extends JpaRepository<StockLimitUpFive,Long> {
    List<StockLimitUpFive> findAll();
    List<StockLimitUpFive> findByCodeAndDayFormat(String code, String dayFormat);
    List<StockLimitUpFive> findByDayFormatAndContinueBoardCountGreaterThan(String dayFormat, int min);
    StockLimitUpFive save(StockLimitUpFive xgbStock);
    List<StockLimitUpFive> findByDayFormatOrderByContinueBoardCountDesc(String dayFormat);
    @Query(value="SELECT * from stock_limit_up_five WHERE day_format BETWEEN ?1 AND ?2", nativeQuery = true)
    public List<StockLimitUpFive> fiveStatistic(String start, String end);

}
