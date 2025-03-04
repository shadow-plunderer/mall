package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.entity.InventoryInfo;

import java.util.List;

public interface IInventoryService extends IService<InventoryInfo> {
    boolean lockInventory(Integer productId, Integer quantity);

    boolean unlockInventory(Integer productId, Integer quantity);

    boolean reduceInventory(Integer productId, Integer quantity);

    List<InventoryInfo> getAllInventory();

    InventoryInfo getInventoryById(Integer id);

    boolean addInventory(InventoryInfo inventoryInfo);

    boolean updateInventoryById(Integer id, InventoryInfo inventoryInfo);

    boolean deleteInventoryById(Integer id);
}
