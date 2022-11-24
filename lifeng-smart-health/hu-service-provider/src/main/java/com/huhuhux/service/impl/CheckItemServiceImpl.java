package com.huhuhux.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.huhuhux.doman.CheckItem;
import com.huhuhux.mapper.CheckItemMapper;
import com.huhuhux.service.CheckItemService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@DubboService
public class CheckItemServiceImpl  implements CheckItemService {

    @Autowired
    private CheckItemMapper checkItemMapper;

    @Override
    public boolean save(CheckItem checkItem) {

        return checkItemMapper.insert(checkItem)>0;
    }

    public IPage<CheckItem> getPage(int currentPage, int pageSize){
        System.out.println("selectAll被调用了..................");
        IPage page = new Page(currentPage,pageSize);
        checkItemMapper.selectPage(page,null);
       return page;
    }

    public boolean updateById(CheckItem checkItem){
        return checkItemMapper.updateById(checkItem)>0;
    }

    @Override
    public CheckItem selectById(Integer id) {

        return checkItemMapper.selectById(id);
    }

    public boolean deleteById(Integer id){

        System.out.println("-----------------------"+id+"++++++++++++++++++");
        return checkItemMapper.deleteById(id)>0;
    }

    @Override
    public List<CheckItem> findAll() {
        return checkItemMapper.selectList(null);
    }

}
