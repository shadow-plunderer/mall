package org.example.mq;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.example.conf.RequestContextTtl;
import org.example.service.IOrderService;
import org.example.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Component
@RocketMQMessageListener(topic = "order-timeout-topic", consumerGroup = "order-consumer-group")
public class OrderTimeoutListener implements RocketMQListener<String> {

    @Autowired
    private IOrderService orderService;

    @Override
    public void onMessage(String orderId) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        RequestContextTtl.set(requestAttributes);
        Log.debug("🚀 消息消费成功, 订单ID: " + orderId);
        orderService.handleOrderTimeout(orderId);
    }
}
