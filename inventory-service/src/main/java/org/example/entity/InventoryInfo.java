package org.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("inventory")
public class InventoryInfo {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer productId;
    private Integer stock;
    private Integer reserveStock;
}
