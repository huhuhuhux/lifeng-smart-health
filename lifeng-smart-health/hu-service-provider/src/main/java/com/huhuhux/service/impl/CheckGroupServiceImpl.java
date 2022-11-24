package com.huhuhux.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.huhuhux.doman.CheckGroup;
import com.huhuhux.doman.CheckItem;
import com.huhuhux.mapper.CheckGroupMapper;
import com.huhuhux.service.CheckGroupService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DubboService
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    CheckGroupMapper checkGroupMapper;


    @Override
    public boolean save( Integer[] checkitemIds, CheckGroup checkGroup) {
        System.out.println("----------------------------------------------");
        System.out.println(checkGroup);
        checkGroupMapper.save(checkGroup);
        setCheckGroupAndCheckItem(checkGroup.getId(),checkitemIds);
        return false;
    }

    private void setCheckGroupAndCheckItem(Integer checkGroupId, Integer[] checkitemIds) {

        if (checkitemIds != null && checkitemIds.length > 0) {
            for (Integer checkitemId:checkitemIds) {
                Map<String,Integer> map = new HashMap();
                map.put("checkgroup_id", checkGroupId);
                map.put("checkitem_id", checkitemId);
                checkGroupMapper.setCheckGroupAndCheckItem(map);
            }

        }
    }

    @Override
    public IPage<CheckGroup> getPage(int currentPage, int pageSize) {
        IPage page = new Page(currentPage,pageSize);

        checkGroupMapper.selectPage(page);

        return page;
    }

    @Override
    public boolean deleteById(Integer id) {

        checkGroupMapper.deleteAssociation(id);

        checkGroupMapper.deleteById(id);
        return true;
    }

    @Override
    public boolean updateById(Integer[] checkitemIds,CheckGroup checkGroup) {
        // 根据检查组id删除中间表数据（清理原有关联关系）
        checkGroupMapper.deleteAssociation(checkGroup.getId());
        // 向中间表(t_checkgroup_checkitem)插入数据（建立检查组和检查项关联关系）
        setCheckGroupAndCheckItem(checkGroup.getId(), checkitemIds);
        // 更新检查组基本信息
        checkGroupMapper.upData(checkGroup);
        return true;
    }

    @Override
    public CheckItem selectById(Integer id) {
        return null;
    }

    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupMapper.findById(id);
    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        return checkGroupMapper.findCheckItemIdsByCheckGroupId(id);
    }


}
