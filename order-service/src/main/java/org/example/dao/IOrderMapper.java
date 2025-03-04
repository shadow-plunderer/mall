package org.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.example.entiry.OrderInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderMapper extends BaseMapper<OrderInfo> {
}
