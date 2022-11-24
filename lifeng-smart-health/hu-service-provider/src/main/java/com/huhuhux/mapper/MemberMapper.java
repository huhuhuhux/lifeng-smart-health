package com.huhuhux.mapper;

import com.github.pagehelper.Page;

import com.huhuhux.doman.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {

    void insert(Member member);

    Member findByPhone(String telephone);


    Member findByTelephone(String telephone);

    void add(Member member);
}
