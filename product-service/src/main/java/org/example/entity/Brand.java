package org.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("brands")
public class Brand {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String logo;
}
