package zrgj.order_everyday.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {
    @Autowired
    private SimpMessagingTemplate template;

    public void send(String message) throws Exception {
        this.template.convertAndSend("/topic/shit", message);
    }
}
