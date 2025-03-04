package org.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.example.entity.InventoryInfo;

public interface IInventoryMapper extends BaseMapper<InventoryInfo> {
    int lockInventory(@Param("productId") Integer productId, @Param("quantity") Integer quantity);
}
