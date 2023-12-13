package com.leepay.payrollcalc.websocket;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class WebSocketEventListener {

    private static final String SUBSCRIBER_COUNT_DESTINATION = "/topic/subscriberCount";

    private final SimpMessagingTemplate messagingTemplate;
    private final AtomicInteger connectedUsers = new AtomicInteger();

    public WebSocketEventListener(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        int count = connectedUsers.incrementAndGet();
        messagingTemplate.convertAndSend(SUBSCRIBER_COUNT_DESTINATION, count);
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        int count = connectedUsers.decrementAndGet();
        messagingTemplate.convertAndSend(SUBSCRIBER_COUNT_DESTINATION, count);
    }
}

