package com.example.demo.dao;

import com.example.demo.domain.MyTotalStock;
import com.example.demo.domain.table.StockDay;
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
public interface StockDayRepository extends JpaRepository<StockDay,Long> {
    List<StockDay> findAll();
    StockDay findByCodeAndDayFormat(String code, String dayFormat);
    List<StockDay> findByDayFormatOrderByHotSort(String dayFormat);
    List<StockDay> findByDayFormatOrderByHotSevenDesc(String dayFormat);
    List<StockDay> findByDayFormatOrderByOpenBidRateDesc(String dayFormat);
    List<StockDay> findByDayFormatOrderByOpenBidRate(String dayFormat);
    StockDay save(StockDay StockDay);
    @Query(value="SELECT * FROM ( SELECT *, COUNT(id) as total_count from stock_day WHERE day_format BETWEEN ?1 AND ?2  GROUP BY code) as temp WHERE temp.total_count>2 ORDER BY total_count DESC ", nativeQuery = true)
    public List<StockDay> totalCount(String start, String end);

    @Query(value="SELECT * FROM ( SELECT code, name,sum(hot_seven) as hot_seven,sum(hot_value)as hot_value, COUNT(id) as total_count from stock_day WHERE day_format BETWEEN ?1 AND ?2  GROUP BY code) as temp WHERE temp.total_count>2 ORDER BY total_count DESC ", nativeQuery = true)
    public List<MyTotalStock> fiveInfo(String start, String end);
    @Query(value="SELECT * FROM ( SELECT code, name,sum(hot_seven) as hot_seven,sum(hot_value)as hot_value, COUNT(id) as total_count from stock_day WHERE day_format BETWEEN ?1 AND ?2  GROUP BY code) as temp  ORDER BY total_count DESC LIMIT 4", nativeQuery = true)
    public List<MyTotalStock> limit4(String start, String end);

}
