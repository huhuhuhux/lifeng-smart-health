package com.huhuhux.config;

import com.huhuhux.job.ClearSetmealPicJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScheduleConfig {
    public static class JobDemoSchedule {

        @Bean
        public JobDetail clearSetmealPicJob() {
            // 返回 Job
            return JobBuilder.newJob(ClearSetmealPicJob.class)
                    .withIdentity("clearSetmealPicJob")
                    .storeDurably(true) // 保留JobDetail
                    .build();
        }

        @Bean
        public Trigger clearSetmealPicJobTrigger() {
            // 创建简单调度计划
            SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInSeconds(10) // 频率。
                    .repeatForever(); // 次数。
            // 创建Cron调度计划
            // 返回 Trigger 构造器
            return TriggerBuilder.newTrigger()
                    .forJob(clearSetmealPicJob())
                    .withSchedule(simpleScheduleBuilder)
                    .withIdentity("clearSetmealPicJobTrigger")
                    .build();
        }
    }

}
