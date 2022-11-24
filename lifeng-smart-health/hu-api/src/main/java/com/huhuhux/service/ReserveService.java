package com.huhuhux.service;


import com.huhuhux.util.R;

import java.util.Map;

public interface ReserveService {

    R reserveProcess(Map map) throws Exception;
     Map getById(Integer id) throws Exception;
}
