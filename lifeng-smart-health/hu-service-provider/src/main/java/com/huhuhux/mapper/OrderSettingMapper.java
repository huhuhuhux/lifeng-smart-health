package com.huhuhux.mapper;



import cn.hutool.core.date.DateTime;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huhuhux.doman.OrderSetting;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderSettingMapper extends BaseMapper<OrderSetting> {
    long selectCountByDate(Date orderDate);
    void updateNumberByDate(OrderSetting orderSetting);
    void add(OrderSetting orderSetting);
    List<OrderSetting> selectByMonth(Map<String, String> map);
    OrderSetting selectByDate(DateTime reserveDate);
    void update(OrderSetting reserveSetting);
}
