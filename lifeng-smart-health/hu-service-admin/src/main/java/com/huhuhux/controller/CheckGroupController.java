package com.huhuhux.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;

import com.huhuhux.constant.MessageConstant;
import com.huhuhux.doman.CheckGroup;
import com.huhuhux.service.CheckGroupService;
import com.huhuhux.util.QueryPageBean;
import com.huhuhux.util.R;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {

    @DubboReference
    CheckGroupService checkGroupService;

    @PostMapping("/findPage")
    public R getPage(@RequestBody QueryPageBean queryPageBean){

        IPage page = checkGroupService.getPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        return new R(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS, page);
    }

    @PostMapping("add")
    public R save(@RequestParam("checkitemIds")Integer[] checkitemIds, @RequestBody CheckGroup checkGroup){

       return new R(checkGroupService.save(checkitemIds,checkGroup),MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    @GetMapping("findById")
    public R findById(@RequestParam("id")Integer id){

        return new R(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroupService.findById(id));
    }

    @GetMapping("findCheckItemIdsByCheckGroupId")
    public R findCheckItemIdsByCheckGroupId(@RequestParam("id") Integer id){
        return new R(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkGroupService.findCheckItemIdsByCheckGroupId(id));
    }

    @PostMapping("edit")
    public R upDataById(@RequestParam("checkitemIds")Integer[] checkitemIds, @RequestBody CheckGroup checkGroup){
        return new R(checkGroupService.updateById(checkitemIds,checkGroup),MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

    @GetMapping("delete")
    public R deleteById(@RequestParam("id") Integer id){
        return new R(checkGroupService.deleteById(id),MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }
}
