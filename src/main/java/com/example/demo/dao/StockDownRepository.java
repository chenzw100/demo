package com.example.demo.dao;

import com.example.demo.domain.table.StockDown;
import org.springframework.data.jpa.repository.JpaRepository;

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
public interface StockDownRepository extends JpaRepository<StockDown,Long> {
    List<StockDown> findAll();
    List<StockDown> findByCodeAndDayFormat(String code, String dayFormat);
    List<StockDown> findByDayFormatOrderByOpenBidRate(String dayFormat);
    StockDown save(StockDown stockDown);

}
