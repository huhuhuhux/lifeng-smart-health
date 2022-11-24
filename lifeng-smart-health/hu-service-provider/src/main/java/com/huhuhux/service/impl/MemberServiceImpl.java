package com.huhuhux.service.impl;

import com.huhuhux.doman.Member;

import com.huhuhux.mapper.MemberMapper;
import com.huhuhux.service.MemberService;

import com.huhuhux.util.MD5Utils;
import org.apache.dubbo.config.annotation.DubboService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberMapper memberMapper;

    // 根据手机号查询会员
    public Member findByTelephone(String telephone) {
        return memberMapper.findByPhone(telephone);
    }
    // 新增会员
    public void add(Member member) {
        if(member.getPassword() != null) {
            member.setPassword(MD5Utils.encode(member.getPassword()));
        }
        memberMapper.add(member);
    }
}
