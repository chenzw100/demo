package com.example.demo.dao;

import com.example.demo.domain.table.StockSpaceHeight;
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
public interface StockSpaceHeightRepository extends JpaRepository<StockSpaceHeight,Long> {
    List<StockSpaceHeight> findAll();
    List<StockSpaceHeight> findByDayFormat(String dayFormat);
    StockSpaceHeight save(StockSpaceHeight downStock);
    @Query(value=" SELECT * FROM stock_space_height WHERE  day_format BETWEEN ?1 AND ?2 ORDER BY id ", nativeQuery = true)
    public List<StockSpaceHeight> close(String start, String end);

}
