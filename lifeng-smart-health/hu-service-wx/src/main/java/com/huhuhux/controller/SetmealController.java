package com.huhuhux.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;

import com.huhuhux.constant.MessageConstant;
import com.huhuhux.doman.Setmeal;
import com.huhuhux.service.SetmealService;
import com.huhuhux.util.QueryPageBean;
import com.huhuhux.util.R;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("setmeal")
public class SetmealController {

    @DubboReference
    SetmealService setmealService;

    @PostMapping("getAllSetmeal.do")
    public R getAllSetmeal(@RequestBody QueryPageBean queryPageBean) {
        IPage page = setmealService.getPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

        return new R(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS, page);
    }

    @PostMapping("/findById")
    public R findById(@RequestParam("id") Integer id){
        try{
            Setmeal setmeal = setmealService.findById(id);
            return new R(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        }catch (Exception e){
            e.printStackTrace();
            return new R(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }

}
