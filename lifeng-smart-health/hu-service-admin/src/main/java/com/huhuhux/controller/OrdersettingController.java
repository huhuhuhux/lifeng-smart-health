package com.huhuhux.controller;


import com.huhuhux.constant.MessageConstant;
import com.huhuhux.doman.OrderSetting;
import com.huhuhux.service.OrderSettingService;
import com.huhuhux.util.PoiUtils;
import com.huhuhux.util.R;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("ordersetting")
public class OrdersettingController {

    @DubboReference
    OrderSettingService orderSettingService;

    /**
     * 文件上传，实现预约设置数据批量导入
     * @param excelFile
     * @return
     */
    @RequestMapping("/upload")
    public R upload(@RequestParam("excelFile") MultipartFile excelFile){
        try {
            // 使用POI解析表格数据
            List<String[]> list = PoiUtils.readExcel(excelFile);
            List<OrderSetting> data = new ArrayList<>();
            for (String[] strings : list) {
                String orderDate = strings[0];
                String number = strings[1];
                OrderSetting orderSetting = new OrderSetting(new Date(orderDate),Integer.parseInt(number));
                data.add(orderSetting);
            }
            // 通过 dubbo 远程调用服务实现数据批量导入到数据库
            orderSettingService.add(data);
            return new R(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
            // 文件解析失败
            return new R(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }

    @RequestMapping("/getOrderSettingByMonth")
    public R getOrderSettingByMonth(String date){
        try{
            List<Map> list = orderSettingService.getOrderSettingByMonth(date);
            return new R(true,MessageConstant.GET_ORDERSETTING_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            return new R(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

    @PostMapping("editNumberByDate")
    public R editNumberByDate(@RequestBody OrderSetting orderSetting){

        orderSettingService.editNumberByDate(orderSetting);
        return new R(true,MessageConstant.ORDERSETTING_SUCCESS);
    }


}
