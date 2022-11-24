package com.huhuhux.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;

import com.huhuhux.constant.MessageConstant;
import com.huhuhux.constant.RedisConstant;
import com.huhuhux.doman.Setmeal;
import com.huhuhux.service.SetmealService;
import com.huhuhux.util.QiniuUtils;
import com.huhuhux.util.QueryPageBean;
import com.huhuhux.util.R;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("setmeal")
public class SetmealController {

    @DubboReference
    SetmealService setmealService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @RequestMapping("/upload")
    public R upload(@RequestParam("imgFile")MultipartFile imgFile){

        try {
            //获取文件名
            String originalFilename = imgFile.getOriginalFilename();
            int lastIndexOf = originalFilename.lastIndexOf(".");
            //获取文件名后坠
            String suffix = originalFilename.substring(lastIndexOf - 1);
            //使用uuid生产文件名，防止重复
            String fileName = UUID.randomUUID().toString()+suffix;
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);

            //将图片存入redis
            redisTemplate.opsForSet().add(RedisConstant.SETMEAL_PIC_KEY, fileName);

            R result = new R(true, MessageConstant.PIC_UPLOAD_SUCCESS,fileName);

            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return new R(false,MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    @RequestMapping("/add")
    public R add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds){
        try {
            setmealService.add(setmeal,checkgroupIds);
        }catch (Exception e){
            //新增套餐失败
            return new R(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
        // 新增套餐成功
        return new R(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    @PostMapping("/findPage")
    public R getPage(@RequestBody QueryPageBean queryPageBean){

        IPage page = setmealService.getPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        return new R(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS, page);
    }

}
