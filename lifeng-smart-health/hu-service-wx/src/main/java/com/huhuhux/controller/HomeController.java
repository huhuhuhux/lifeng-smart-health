package com.huhuhux.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomeController {

//    @GetMapping("/")
//    public String gotoIndex() {
//        return "index";
//    }

    @RequestMapping("/m_setmeal")
    public String gotom_setmeal() {
        return "m_setmeal";
    }

    @RequestMapping("/setmeal")
    public String gotoSetmeal() {
        return "setmeal";
    }

    @GetMapping("/setmeal_detail")
    public String gotoSetmealDetail() {
        return "setmeal_detail";
    }

    @GetMapping("/orderInfo")
    public String gotoOrderInfo(String id) {
        return "orderInfo";
    }
    @GetMapping("/orderNotice")
    public String gotoOrderNotice() {
        return "orderNotice";
    }

    @GetMapping("/orderSuccess")
    public String gotoOrderSuccess(String id) {
        return "orderSuccess";
    }

    @RequestMapping("/setmeal_detail_5.html")
    public String gotom_setmeal_detail_5() {
        return "setmeal_detail_5";
    }

    @RequestMapping("orderInfo.html")
    public String gotu_orderInfo(){
        return "orderInfo";
    }

    @RequestMapping("index.html")
    public String gotoIndex(){
        return "index";
    }

    @RequestMapping("/")
    public String gotuLongin(){
        return "login";
    }
}

