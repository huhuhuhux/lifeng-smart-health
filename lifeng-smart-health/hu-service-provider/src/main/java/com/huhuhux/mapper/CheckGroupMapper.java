package com.huhuhux.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.huhuhux.doman.CheckGroup;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CheckGroupMapper extends BaseMapper<CheckGroup> {

    IPage<CheckGroup> selectPage(IPage page);

    void save(CheckGroup checkGroup);

    boolean setCheckGroupAndCheckItem(Map map);

    CheckGroup findById(Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    void deleteAssociation(Integer id);

    void upData(CheckGroup checkGroup);

    void deleteById(Integer id);
}
