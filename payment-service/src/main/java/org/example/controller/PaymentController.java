package org.example.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.example.conf.AlipayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private AlipayConfig alipayConfig;

    @PostMapping("/pay")
    public String payOrder(@RequestParam("orderId") String orderId,
                           @RequestParam("amount") String amount) {
        // Initialize Alipay client
        AlipayClient alipayClient = new DefaultAlipayClient(
                alipayConfig.getGatewayUrl(),
                alipayConfig.getAppId(),
                alipayConfig.getMerchantPrivateKey(),
                "json",
                alipayConfig.getCharset(),
                alipayConfig.getAlipayPublicKey(),
                alipayConfig.getSignType()
        );

        // Create a request
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(alipayConfig.getReturnUrl());
        alipayRequest.setNotifyUrl(alipayConfig.getNotifyUrl());

        // Set the business parameters
        Map<String, String> bizContent = new HashMap<>();
        bizContent.put("out_trade_no", orderId);
        bizContent.put("total_amount", amount);
        bizContent.put("subject", "Order Payment");
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
        alipayRequest.setBizContent(JSON.toJSONString(bizContent));

        // Execute the request
        try {
            String result = alipayClient.sdkExecute(alipayRequest).getBody(); // Return the payment page
            return alipayConfig.getGatewayUrl() + "?" + result;
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return "Error occurred while processing payment";
        }
    }

    @PostMapping("/notify")
    public String handleAlipayNotification(@RequestParam Map<String, String> params) {
        // Extract parameters from the request
        Map<String, String> paramsMap = new HashMap<>(params);

        try {
            // Verify the signature
            boolean signVerified = AlipaySignature.rsaCheckV1(paramsMap,
                    alipayConfig.getAlipayPublicKey(),
                    alipayConfig.getCharset(),
                    alipayConfig.getSignType());

            if (signVerified) {
                // Process the notification
                String outTradeNo = paramsMap.get("out_trade_no");
                String tradeStatus = paramsMap.get("trade_status");

                if ("TRADE_SUCCESS".equals(tradeStatus)) {
                    // Update order status to paid
                    // Example: orderService.updateOrderStatus(outTradeNo, "PAID");
                }
                return "success";
            } else {
                return "failure";
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return "failure";
        }
    }
}
