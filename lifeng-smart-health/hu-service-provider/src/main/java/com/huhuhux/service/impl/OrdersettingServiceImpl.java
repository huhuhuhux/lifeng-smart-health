package com.huhuhux.service.impl;


import com.huhuhux.doman.OrderSetting;
import com.huhuhux.mapper.OrderSettingMapper;
import com.huhuhux.service.OrderSettingService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DubboService
public class OrdersettingServiceImpl implements OrderSettingService {

    @Autowired
    OrderSettingMapper orderSettingMapper;

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        orderSettingMapper.updateNumberByDate(orderSetting);
    }

    @Override
    public void add(List<OrderSetting> ordersettingList) {
        if(ordersettingList != null && ordersettingList.size() > 0){
            for (OrderSetting reserveSetting : ordersettingList) {
                // 判断当前日期是否已经进行了预约设置
                long countByOrderDate = orderSettingMapper.selectCountByDate(reserveSetting.getOrderDate());
                if(countByOrderDate > 0){
                    // 已经进行了预约设置，执行更新操作
                    orderSettingMapper.updateNumberByDate(reserveSetting);
                }else{
                    // 没有进行预约设置，执行插入操作
                    orderSettingMapper.add(reserveSetting);
                }
            }
        }
    }

    @Override
    public List<Map> getOrderSettingByMonth(String date) {
        // 2021-8-1
        String begin = date + "-1";
        // 2021-8-31
        String end = date + "-31";
        Map<String,String> map = new HashMap<>();
        map.put("begin",begin);
        map.put("end",end);
        // 调用DAO，根据日期范围查询预约设置数据
        List<OrderSetting> list = orderSettingMapper.selectByMonth(map);
        List<Map> result = new ArrayList<>();
        if(list != null && list.size() > 0){
            for (OrderSetting reserveSetting : list) {
                Map<String,Object> m = new HashMap<>();
                // 获取日期数字（几号）
                m.put("date", reserveSetting.getOrderDate().getDate());
                m.put("number", reserveSetting.getNumber());
                m.put("reservations", reserveSetting.getReservations());
                result.add(m);
            }
        }
        return result;
    }
}
