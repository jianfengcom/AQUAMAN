package com.example.mq;

import com.example.event.SendMsgEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class SendMsgListener implements ApplicationListener<SendMsgEvent> {
    @Override
    public void onApplicationEvent(SendMsgEvent event) {
        System.out.println("有内鬼, 交易终止: " + event.getSendId());
    }
}
