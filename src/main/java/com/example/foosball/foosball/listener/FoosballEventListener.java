package com.example.foosball.foosball.listener;

import com.example.foosball.foosball.service.FoosballService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@Slf4j
public class FoosballEventListener {

    @Autowired
    private FoosballService foosballService;

    @EventListener
    public void onDisconnectEvent(final SessionDisconnectEvent event) {
        log.info("Client {} disconnected", event.getUser());
    }
}
