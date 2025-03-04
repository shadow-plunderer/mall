package org.example.controller;

import org.example.entiry.OrderInfo;
import org.example.service.IOrderService;
import org.example.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @PostMapping("/create")
    public R<OrderInfo> createOrder(@RequestParam("userId") Integer userId, @RequestParam("productId") Integer productId, @RequestParam("quantity") Integer quantity) {
        OrderInfo orderInfo = orderService.createOrder(userId, productId, quantity);
        if (orderInfo == null) {
            return R.error(400, "Error creating order");
        }
        return R.ok(orderInfo);
    }

    @PutMapping("/pay")
    public R<String> payOrder(@RequestParam("orderId") String orderId) {
        orderService.handlePaymentSuccess(orderId);
        return R.ok("Pay order success");
    }

    @GetMapping
    public R<List<OrderInfo>> getAllOrder() {
        List<OrderInfo> orderInfos = orderService.getAllOrder();
        if (CollectionUtils.isEmpty(orderInfos)) {
            return R.error(404, "Order not found");
        }
        return R.ok(orderInfos);
    }

    @GetMapping("/{id}")
    public R<OrderInfo> getOrderById(@PathVariable String id) {
        OrderInfo orderInfo = orderService.getOrderById(id);
        if (orderInfo == null) {
            return R.error(404, "Order not found");
        }
        return R.ok(orderInfo);
    }

    @PostMapping("/add")
    public R<String> addOrder(@RequestBody OrderInfo orderInfo) {
        boolean result = orderService.addOrder(orderInfo);
        if (!result) {
            return R.error(400, "Error adding order");
        }
        return R.ok("Order added");
    }

    @PutMapping("/{id}")
    public R<String> updateOrderById(@PathVariable String id, @RequestBody OrderInfo orderInfo) {
        boolean result = orderService.updateOrderById(id, orderInfo);
        if (!result) {
            return R.error(400, "Error updating order");
        }
        return R.ok("Order updated");
    }

    @DeleteMapping("/{id}")
    public R<String> deleteOrderById(@PathVariable String id) {
        boolean result = orderService.deleteOrderById(id);
        if (!result) {
            return R.error(400, "Error deleting order");
        }
        return R.ok("Order deleted");
    }
}
