package com.huhuhux.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;

import com.huhuhux.constant.MessageConstant;
import com.huhuhux.doman.CheckItem;
import com.huhuhux.service.CheckItemService;
import com.huhuhux.util.QueryPageBean;
import com.huhuhux.util.R;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @DubboReference
    private CheckItemService checkItemService;

    @PostMapping("/add")
    public R add(@RequestBody CheckItem checkItem) {
        checkItemService.save(checkItem);


        return new R(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    @PostMapping("/findPage")
    public R getPage(@RequestBody QueryPageBean queryPageBean){

        IPage page = checkItemService.getPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

       return new R(true, MessageConstant.ADD_CHECKITEM_SUCCESS, page);
    }

    @GetMapping("/findById.do")
    public R selectOne(@RequestParam("id") Integer Id){
        return new R(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkItemService.selectById(Id));
    }

    @GetMapping("/delete.do")
    public R delete(@RequestParam("id")Integer id){
        System.out.println(id);
        return new R(true,MessageConstant.DELETE_CHECKITEM_SUCCESS,checkItemService.deleteById(id));
    }

    @PostMapping("/edit.do")
    public R updateById(@RequestBody CheckItem checkItem){
        return new R(true,MessageConstant.EDIT_CHECKITEM_SUCCESS,checkItemService.updateById(checkItem));
    }

    @PostMapping("findAll")

    public R findAll(){
        return new R(true,MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItemService.findAll());
    }



}
