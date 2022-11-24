package com.huhuhux.service;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huhuhux.doman.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealService {


    IPage getPage(Integer currentPage, Integer pageSize);

    void add(Setmeal setmeal, Integer[] checkgroupIds);

    void setSetmealAndCheckGroup(Map map);

    List<Setmeal> getAll();

    Setmeal findById(int id);
}
