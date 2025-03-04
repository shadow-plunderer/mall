package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.example.conf.RequestContextTtl;
import org.example.dao.IOrderMapper;
import org.example.dto.ProductDto;
import org.example.entiry.OrderInfo;
import org.example.feign.IInventoryFeign;
import org.example.feign.IProductFeign;
import org.example.service.IOrderService;
import org.example.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadPoolExecutor;


@Service
public class OrderServiceImpl extends ServiceImpl<IOrderMapper, OrderInfo> implements IOrderService {

    @Autowired
    IProductFeign productFeign;

    @Autowired
    IInventoryFeign inventoryFeign;

    @Autowired
    ThreadPoolExecutor executor;

    @Autowired
    RocketMQTemplate rocketMQTemplate;

    @Transactional
    @Override
    public OrderInfo createOrder(Integer userId, Integer productId, Integer quantity) {
        OrderInfo order = new OrderInfo();
        order.setId(UUID.randomUUID().toString());
        order.setUserId(userId);
        order.setProductId(productId);
        order.setQuantity(quantity);
        ProductDto productDto = productFeign.getProductById(productId).getData();
        if (productDto == null) {
            Log.error("🔥 消息创建失败: 商品服务异常");
            return null;
        }
        order.setPrice(productDto.getPrice());
        order.setTotalPrice(productDto.getPrice().multiply(new BigDecimal(quantity)));
        // 0-待支付
        order.setStatus(0);
        this.save(order);

        // 锁定库存
        inventoryFeign.lockInventory(productId, quantity);

        // 保存当前线程的上下文交给 mq 的异步线程
        RequestContextTtl.set(RequestContextHolder.getRequestAttributes());
        RequestContextHolder.setRequestAttributes(RequestContextTtl.get());

        // 发送延时消息, 1 分钟后检查订单是否支付
        Message<String> message = MessageBuilder.withPayload(order.getId()).build();
        rocketMQTemplate.syncSend("order-timeout-topic", message, 3000, 5);
        Log.debug("✅ 消息创建成功, 订单: " + order.getId());
        return order;
    }

    @Transactional
    @Override
    public void handlePaymentSuccess(String orderId) {
        OrderInfo order = this.getById(orderId);
        if (order != null && order.getStatus() == 0) {
            order.setStatus(1); // 1-已支付
            this.updateById(order);

            // 调用库存服务减少库存（RPC 调用库存服务）
            inventoryFeign.reduceInventory(order.getProductId(), order.getQuantity());
        }
    }

    @Transactional
    @Override
    public void handleOrderTimeout(String orderId) {
        OrderInfo order = this.getById(orderId);
        if (order != null && order.getStatus() == 0) {
            order.setStatus(2); // 2-已取消
            this.updateById(order);

            // 调用库存服务回滚库存（RPC 调用库存服务）
            inventoryFeign.unlockInventory(order.getProductId(), order.getQuantity());
        }
    }

    @Override
    public List<OrderInfo> getAllOrder() {
        return this.list();
    }

    @Override
    public OrderInfo getOrderById(String id) {
        return this.getById(id);
    }

    @Override
    public boolean addOrder(OrderInfo orderInfo) {
        return save(orderInfo);
    }

    @Override
    public boolean updateOrderById(String id, OrderInfo orderInfo) {
        orderInfo.setId(id);
        return this.updateById(orderInfo);
    }

    @Override
    public boolean deleteOrderById(String id) {
        return this.removeById(id);
    }
}
