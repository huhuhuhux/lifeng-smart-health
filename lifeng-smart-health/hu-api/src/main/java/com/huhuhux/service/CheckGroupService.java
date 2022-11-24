package com.huhuhux.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huhuhux.doman.CheckGroup;
import com.huhuhux.doman.CheckItem;


import java.util.List;

public interface CheckGroupService {
    boolean save(Integer[] checkitemIds,CheckGroup checkGroup);
    IPage<CheckGroup> getPage(int currentPage, int pageSize);
    boolean deleteById(Integer id);
    boolean updateById(Integer[] checkitemIds,CheckGroup checkGroup);
    CheckItem selectById(Integer id);
    CheckGroup findById(Integer id);
    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);
}
