package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.entiry.OrderInfo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IOrderService extends IService<OrderInfo> {
    OrderInfo createOrder(Integer userId, Integer productId, Integer quantity);

    void handlePaymentSuccess(String orderId);

    void handleOrderTimeout(String orderId);

    List<OrderInfo> getAllOrder();

    OrderInfo getOrderById(@PathVariable String id);

    boolean addOrder(OrderInfo orderInfo);

    boolean updateOrderById(@PathVariable String id, @RequestBody OrderInfo orderInfo);

    boolean deleteOrderById(@PathVariable String id);
}
