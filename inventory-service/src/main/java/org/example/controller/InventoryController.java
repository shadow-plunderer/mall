package org.example.controller;

import org.example.entity.InventoryInfo;
import org.example.service.IInventoryService;
import org.example.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private IInventoryService inventoryService;

    @PutMapping("/lock")
    public R<String> lockInventory(@RequestParam("productId") Integer productId, @RequestParam("quantity") Integer quantity) {
        boolean result = inventoryService.lockInventory(productId, quantity);
        if (!result) {
            return R.error(400, "Error locking inventory");
        }
        return R.ok("Inventory locked");
    }

    @PutMapping("/unlock")
    public R<String> unlockInventory(@RequestParam("productId") Integer productId, @RequestParam("quantity") Integer quantity) {
        boolean result = inventoryService.unlockInventory(productId, quantity);
        if (!result) {
            return R.error(400, "Error unlocking inventory");
        }
        return R.ok("Inventory unlocked");
    }

    @PutMapping("/reduce")
    public R<String> reduceInventory(@RequestParam("productId") Integer productId, @RequestParam("quantity") Integer quantity) {
        boolean result = inventoryService.reduceInventory(productId, quantity);
        if (!result) {
            return R.error(400, "Error reducing inventory");
        }
        return R.ok("Inventory reduced");
    }

    @GetMapping
    public R<List<InventoryInfo>> getAllInventory() {
        List<InventoryInfo> inventoryInfos = inventoryService.getAllInventory();
        if (CollectionUtils.isEmpty(inventoryInfos)) {
            return R.error(404, "Inventory not found");
        }
        return R.ok(inventoryInfos);
    }

    @GetMapping("/{id}")
    public R<InventoryInfo> getInventoryById(@PathVariable Integer id) {
        InventoryInfo inventoryInfo = inventoryService.getInventoryById(id);
        if (inventoryInfo == null) {
            return R.error(404, "Inventory not found");
        }
        return R.ok(inventoryInfo);
    }

    @PostMapping("/add")
    public R<String> addInventory(@RequestBody InventoryInfo inventoryInfo) {
        boolean result = inventoryService.addInventory(inventoryInfo);
        if (!result) {
            return R.error(400, "Error adding inventory");
        }
        return R.ok("Inventory added");
    }

    @PutMapping("/{id}")
    public R<String> updateInventoryById(@PathVariable Integer id, @RequestBody InventoryInfo inventoryInfo) {
        boolean result = inventoryService.updateInventoryById(id, inventoryInfo);
        if (!result) {
            return R.error(400, "Error updating inventory");
        }
        return R.ok("Inventory updated");
    }

    @DeleteMapping("/{id}")
    public R<String> deleteInventoryById(@PathVariable Integer id) {
        boolean result = inventoryService.deleteInventoryById(id);
        if (!result) {
            return R.error(400, "Error deleting inventory");
        }
        return R.ok("Inventory deleted");
    }
}
