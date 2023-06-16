package com.websocket.demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.config.WebSocketMessageBrokerStats;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ChatController {

    private final WebSocketMessageBrokerStats webSocketMessageBrokerStats;
    private final MessageDAO messageDAO;

    @MessageMapping("/message")
    @SendTo("/chat/main")
    public ChatMessage message(ChatMessage chatMessage){
        log.info("CHAT MESSAGE    "+chatMessage);
        if (chatMessage.getMessage() != null){
            messageDAO.save(chatMessage);
        }
        log.info(webSocketMessageBrokerStats.getClientInboundExecutorStatsInfo());
        log.info(webSocketMessageBrokerStats.getWebSocketSessionStatsInfo());
        log.info(webSocketMessageBrokerStats.getStompBrokerRelayStatsInfo());
        log.info(webSocketMessageBrokerStats.getSockJsTaskSchedulerStatsInfo());
        log.info(webSocketMessageBrokerStats.getClientOutboundExecutorStatsInfo());
        log.info(webSocketMessageBrokerStats.getStompSubProtocolStatsInfo());
        log.info(String.valueOf(webSocketMessageBrokerStats.getLoggingPeriod()));

        return chatMessage;
    }

    @RequestMapping("/all")
    @ResponseBody
    public ResponseEntity<?> all(ChatMessage chatMessage){
        log.info("CHAT MESSAGE    "+chatMessage);
        Iterable<ChatMessage> all = messageDAO.findAll();

        return ResponseEntity.ok(all);
    }

}
