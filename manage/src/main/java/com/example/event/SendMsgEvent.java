package com.example.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class SendMsgEvent extends ApplicationEvent {
    private Long sendId;

    public SendMsgEvent(Object source) {
        super(source);
    }

    public SendMsgEvent(Long sendId, Object source) {
        super(source);
        this.sendId = sendId;
    }
}
