package com.huhuhux.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;

import com.huhuhux.constant.MessageConstant;
import com.huhuhux.constant.RedisConstant;
import com.huhuhux.service.ReserveService;
import com.huhuhux.util.R;
import com.huhuhux.util.SmsUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("validateCode")
public class ValidateCodeController {

    @DubboReference
    ReserveService reserveService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @PostMapping("orderCode")
    public R orderCode(@RequestParam("telephone") String telephone){
        //随机验证码：4位
        Integer validateCode = RandomUtil.randomInt(1000, 10000);
        // 给用户发验证码
        String sendStatus =    SmsUtils.sendShortMessage(SmsUtils.TEMPLATE_CODE_1, telephone, validateCode.toString());
        if (ObjectUtil.equals(sendStatus, "OK")) {
            // 在redis中，将验证码保存5分钟
            String smsValidKey = SmsUtils.getSmsValidKey(RedisConstant.RESERVE_SMS_KEY, telephone);
            System.out.println("你好啊啊啊啊 啊啊");
            System.out.println(smsValidKey);
            redisTemplate.opsForValue().set(smsValidKey, String.valueOf(validateCode));
            redisTemplate.expire(smsValidKey, 5, TimeUnit.MINUTES);
            return new R(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } {
            return new R(true, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }

    @PostMapping("sendLogin")
    public R sendLogin(String telephone) {
        //随机验证码：4位
        int validateCode = RandomUtil.randomInt(1000, 10000);
        // 给用户发验证码
        String sendStatus =  SmsUtils.sendShortMessage(SmsUtils.TEMPLATE_CODE_1, telephone, Integer.toString(validateCode));
        if (ObjectUtil.equals(sendStatus, "OK")) {
            // 在redis中，将验证码保存5分钟
            String smsValidKey = SmsUtils.getSmsValidKey(RedisConstant.SENDTYPE_LOGIN, telephone);
            redisTemplate.opsForValue().set(smsValidKey, String.valueOf(validateCode));
            redisTemplate.expire(smsValidKey, 5, TimeUnit.MINUTES);
            return new R(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } else {
            return new R(true, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }



}
