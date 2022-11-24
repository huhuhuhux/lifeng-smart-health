package com.huhuhux.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.huhuhux.doman.Setmeal;
import com.huhuhux.mapper.SetmealMapper;
import com.huhuhux.service.SetmealService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DubboService
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    SetmealMapper setmealMapper;

    @Value("${out-put-path}")
    private String outPutPath;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Override
    public IPage getPage(Integer currentPage, Integer pageSize) {

        IPage page = new Page(currentPage,pageSize);

        return setmealMapper.selectPage(page);
    }

    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealMapper.add(setmeal);
        if (checkgroupIds != null && checkgroupIds.length > 0) {
            for (Integer checkgroupId : checkgroupIds) {
                Map<Integer,Integer> map = new HashMap<>();
                map.put(setmeal.getId(),setmeal.getId());
                setSetmealAndCheckGroup(map);
            }
        }

      /*  // 当添加套餐后需要重新生成静态页面（套餐列表页面、套餐详情页面）
        generateMobileStaticHtml(setmeal.getId());*/
    }

    @Override
    public void setSetmealAndCheckGroup(Map map) {
        setmealMapper.setSetmealAndCheckGroup(map);
    }

    @Override
    public List<Setmeal> getAll() {
        return setmealMapper.getAll();
    }

    @Override
    public Setmeal findById(int id) {
        return setmealMapper.findById(id);
    }
/*
*
     * 生成当前方法所需的静态页面
     */

    public void generateMobileStaticHtml(Integer setmeal_id) {
        //生成静态页面之前需要查询数据
        List<Setmeal> list = setmealMapper.getAll();
        Setmeal setmeal = setmealMapper.findById(setmeal_id);
        //生成套餐列表静态页面
        generateMobileSetmealListHtml(list);
        //生成一个套餐详情静态页面
        generateMobileSetmealDetailHtml(setmeal);
    }

/**
     * 通用方法，（传入所需参数）生成静态页面
     *
     * @param templateName
     * @param htmlPageName
     * @param map*/


    public void generateHtml(String templateName, String htmlPageName, Map map) {
//        获取FreeMarker 配置对象
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Writer out = null;
        try {
            Template template = configuration.getTemplate(templateName);
            out = new FileWriter(new File(outPutPath + "/" + htmlPageName));
            template.process(map, out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

/**
     * 生成套餐列表静态页面
     *
     * @param list*/


    public void generateMobileSetmealListHtml(List<Setmeal> list) {
        Map map = new HashMap();
        map.put("setmealList", list);
        generateHtml("mobile_setmeal.ftl", "m_setmeal.html", map);
    }

/**
     * 生成每一个套餐详情静态页面
     **/


    public void generateMobileSetmealDetailHtml(Setmeal setmeal) {
        
        Map map = new HashMap();
        map.put("setmeal", setmealMapper.findById(setmeal.getId()));
        generateHtml("mobile_setmeal_detail.ftl", "setmeal_detail_" + setmeal.getId() + ".html", map);
    }
}
