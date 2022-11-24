package com.huhuhux.service;

import com.huhuhux.doman.Member;

public interface MemberService {
    Member findByTelephone(String telephone);

    void add(Member member);
}

