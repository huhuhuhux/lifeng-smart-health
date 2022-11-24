package com.huhuhux.controller;


import cn.hutool.core.util.ObjectUtil;

import com.huhuhux.constant.MessageConstant;
import com.huhuhux.constant.RedisConstant;
import com.huhuhux.doman.Reserve;
import com.huhuhux.service.ReserveService;
import com.huhuhux.util.R;
import com.huhuhux.util.SmsUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("reserve")
public class ReserveController {

    @DubboReference
    ReserveService reserveService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @PostMapping("submit")
    public R submit(@RequestBody Map map){
        String telephone = (String) map.get("telephone");
        String validateCode = (String) map.get("validateCode");
        // 从Redis中获取保存的验证码
        String smsValidKey = SmsUtils.getSmsValidKey(RedisConstant.RESERVE_SMS_KEY, telephone);
        String validateCodeInRedis = redisTemplate.opsForValue().get(smsValidKey);

        // 将用户输入的验证码和Redis中保存的验证码进行比对
        if (ObjectUtil.equals(validateCode, validateCodeInRedis)) {
            System.out.println("验证成功");
            // 设置预约类型，可以分为微信预约、电话预约等
            map.put("reserveType", Reserve.ORDERTYPE_WEIXIN);
            R result = null;
            try {
                // 通过Dubbo远程调用服务处理在线预约业务
                result = reserveService.reserveProcess(map);
            } catch (Exception e) {
                e.printStackTrace();
                return result;
            }
            if (result.isFlag()) {
                // 预约成功，可以为用户发送预约成功的短信
                SmsUtils.sendShortMessage(SmsUtils.TEMPLATE_CODE_2, telephone, (String) map.get("orderDate"));
            }
            return result;
        } else {
            // 如果比对不成功，返回结果给页面
            return new R(false, MessageConstant.VALIDATECODE_ERROR);
        }
    }

    @PostMapping("reserve")
    public R reserve(@RequestParam("id") Integer id) throws Exception {
        return new R(true,MessageConstant.ORDER_SUCCESS,reserveService.getById(id));
    }


}
