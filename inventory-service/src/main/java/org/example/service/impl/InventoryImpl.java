package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.dao.IInventoryMapper;
import org.example.entity.InventoryInfo;
import org.example.service.IInventoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryImpl extends ServiceImpl<IInventoryMapper, InventoryInfo> implements IInventoryService {
    @Override
    public boolean lockInventory(Integer productId, Integer quantity) {
        int rowsAffected = baseMapper.lockInventory(productId, quantity);
        return rowsAffected > 0;
    }

    @Override
    public boolean unlockInventory(Integer productId, Integer quantity) {
        return this.update(new LambdaUpdateWrapper<InventoryInfo>()
                .eq(InventoryInfo::getProductId, productId)
                .ge(InventoryInfo::getReserveStock, quantity)
                .setSql("reserve_stock = reserve_stock - " + quantity));
    }

    @Override
    public boolean reduceInventory(Integer productId, Integer quantity) {
        return this.update(new LambdaUpdateWrapper<InventoryInfo>()
                .eq(InventoryInfo::getProductId, productId)
                .setSql("reserve_stock = reserve_stock - " + quantity
                        + ", stock = stock - " + quantity));
    }

    @Override
    public List<InventoryInfo> getAllInventory() {
        return this.list();
    }

    @Override
    public InventoryInfo getInventoryById(Integer id) {
        return this.getById(id);
    }

    @Override
    public boolean addInventory(InventoryInfo inventoryInfo) {
        return this.save(inventoryInfo);
    }

    @Override
    public boolean updateInventoryById(Integer id, InventoryInfo inventoryInfo) {
        inventoryInfo.setId(id);
        return this.updateById(inventoryInfo);
    }

    @Override
    public boolean deleteInventoryById(Integer id) {
        return this.removeById(id);
    }
}
