package com.huhuhux.service;



import com.huhuhux.doman.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {

    void editNumberByDate(OrderSetting orderSetting);

    public void add(List<OrderSetting> ordersettingList);

    List<Map> getOrderSettingByMonth(String date);
}
