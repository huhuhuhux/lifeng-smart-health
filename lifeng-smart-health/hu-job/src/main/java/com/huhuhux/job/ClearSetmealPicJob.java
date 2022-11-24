package com.huhuhux.job;


import com.huhuhux.constant.RedisConstant;
import com.huhuhux.util.QiniuUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Set;

@Slf4j
public class ClearSetmealPicJob extends QuartzJobBean {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        clearSetmealImg();
    }

    /**
     * 清理图片
     */
    public void clearSetmealImg() {
        log.info("[clearSetmealImg][开始执行清理套餐垃圾图片任务]-[{}]");
        // 根据 Redis 中保存的两个 set 集合进行差值计算，获得垃圾图片名称集合
        Set<String> set = redisTemplate.opsForSet().difference(RedisConstant.SETMEAL_PIC_KEY, RedisConstant.SETMEAL_PIC_DB_KEY);
        if (set != null) {
            for (String picName : set) {
                //删除七牛云服务器上的图片
                QiniuUtils.deleteFileFromQiniu(picName);
                //从Redis集合中删除图片名称
                redisTemplate.opsForSet().remove(RedisConstant.SETMEAL_PIC_KEY, picName);
                log.info("[clearSetmealImg][执行清理套餐垃圾图片任务]-[{}]", picName);
            }
        }
        log.info("[clearSetmealImg][结束执行清理套餐垃圾图片任务]-[{}]");
    }


}
