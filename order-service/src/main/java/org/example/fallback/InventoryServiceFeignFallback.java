package org.example.fallback;

import org.example.dto.InventoryDto;
import org.example.feign.IInventoryFeign;
import org.example.vo.R;
import org.springframework.stereotype.Component;

@Component
public class InventoryServiceFeignFallback implements IInventoryFeign {
    @Override
    public R<InventoryDto> getInventoryById(Integer id) {
        return R.error(500, "Inventory service error");
    }

    @Override
    public R<String> lockInventory(Integer productId, Integer quantity) {
        return R.error(500, "Inventory service error");
    }

    @Override
    public R<String> unlockInventory(Integer productId, Integer quantity) {
        return R.error(500, "Inventory service error");
    }

    @Override
    public R<String> reduceInventory(Integer productId, Integer quantity) {
        return R.error(500, "Inventory service error");
    }
}
