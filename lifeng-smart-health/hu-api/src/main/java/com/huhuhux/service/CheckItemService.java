package com.huhuhux.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huhuhux.doman.CheckItem;

import java.util.List;

public interface CheckItemService {
    /**
     * 添加检查项
     * @param checkItem
     * @return
     */
    boolean save(CheckItem checkItem);
    IPage<CheckItem> getPage(int currentPage, int pageSize);
    boolean deleteById(Integer checkItem);
    boolean updateById(CheckItem checkItem);
    CheckItem selectById(Integer id);

    List<CheckItem> findAll();
}
