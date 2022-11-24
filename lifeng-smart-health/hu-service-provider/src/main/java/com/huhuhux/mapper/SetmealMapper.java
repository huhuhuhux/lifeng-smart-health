package com.huhuhux.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.huhuhux.doman.Setmeal;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SetmealMapper extends BaseMapper<Setmeal> {
    IPage selectPage(IPage page);
    void add(Setmeal setmeal);
    void setSetmealAndCheckGroup(Map map);
    List<Setmeal> getAll();

    Setmeal findById(int id);
}lifeng-smart-health
