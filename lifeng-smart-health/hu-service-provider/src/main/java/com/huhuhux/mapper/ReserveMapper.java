package com.huhuhux.mapper;


import com.huhuhux.doman.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReserveMapper {

    void insert(Order reserve);

    List<Order> selectList(Order reserve);

    /**
     * 根据id找成功预约的信息(需要三表Join，用member_id找预约人名称，用setmeal_id找套餐名，剩余都能在t_order中找到)
     * @param id
     * @return
     */
    Map selectOne(Integer id);
}
