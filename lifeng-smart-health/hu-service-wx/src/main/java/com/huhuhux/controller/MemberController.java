package com.huhuhux.controller;

import com.alibaba.fastjson.JSON;
import com.huhuhux.constant.MessageConstant;
import com.huhuhux.constant.RedisConstant;
import com.huhuhux.doman.Member;
import com.huhuhux.service.MemberService;
import com.huhuhux.util.R;
import com.huhuhux.util.SmsUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * 处理会员相关操作
 *
 * @author rushuni
 * @date 2021/08/12
 */
@RestController
@RequestMapping("/login")
public class MemberController {

    @Autowired
    private JedisPool jedisPool;

    @DubboReference
    private MemberService memberService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    /**
     * 手机号快速登录
     * 
     * @param response
     * @param map
     * @return
     */
    @RequestMapping("/check")
    public R login(HttpServletResponse response, @RequestBody Map map){
        String telephone = (String) map.get("telephone");
        String validateCode = (String) map.get("validateCode");
        // 从Redis中获取保存的验证码
        /*String validateCodeInRedis = jedisPool.getResource().get( RedisConstant.SENDTYPE_LOGIN+":"+telephone );*/

        String smsValidKey = SmsUtils.getSmsValidKey(RedisConstant.SENDTYPE_LOGIN, telephone);
        String validateCodeInRedis = redisTemplate.opsForValue().get(smsValidKey);

        if(validateCode != null && validateCode.equals(validateCodeInRedis)){
            // 验证码输入正确
            // 判断当前用户是否为会员（查询会员表来确定）
            Member member = memberService.findByTelephone(telephone);
            if(member == null){
                member = new Member();
                // 不是会员，自动完成注册（自动将当前用户信息保存到会员表）
                member.setRegTime(new Date());
                member.setPhoneNumber(telephone);
                // 设置初始密码 123
                member.setPassword("123");
                memberService.add(member);
            }
            // 向客户端浏览器写入Cookie，内容为手机号
            Cookie cookie = new Cookie("login_member_telephone",telephone);
            cookie.setPath("/");
            cookie.setMaxAge(60*60*24*30);
            response.addCookie(cookie);
            // 将会员信息保存到Redis
//            String json = JSON.toJSON(member).toString();
//            jedisPool.getResource().setex(telephone,60*30L,json);
            return new R(true, MessageConstant.LOGIN_SUCCESS);

        }else{
            // 验证码输入错误
            return new R(false, MessageConstant.VALIDATECODE_ERROR);
        }
    }
}