package zrgj.order_everyday.controller;

import com.auth0.jwt.interfaces.Claim;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zrgj.order_everyday.entity.Notification;
import zrgj.order_everyday.pojo.dto.ResultMap;
import zrgj.order_everyday.service.NotificationService;
import zrgj.order_everyday.util.JWTUtil;
import zrgj.order_everyday.util.ResponseWrapper;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@ServerEndpoint(value = "/listen/notification")
@RequestMapping("/notification")
@RestController
@Component
public class NotificationController {


    /**
     * 记录当前在线连接数
     */
    private static AtomicInteger onlineCount = new AtomicInteger(0);

    /**
     * 存放所有在线的客户端
     */
    private static Map<String, Session> clients = new ConcurrentHashMap<>();

    @Autowired
    private NotificationService notificationService;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        onlineCount.incrementAndGet(); // 在线数加1
        clients.put(session.getId(), session);
        log.info("有新连接加入：{}，当前在线人数为：{}", session.getId(), onlineCount.get());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        onlineCount.decrementAndGet(); // 在线数减1
        clients.remove(session.getId());
        log.info("有一连接关闭：{}，当前在线人数为：{}", session.getId(), onlineCount.get());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        // 点餐系统用不到
        log.info("服务端收到客户端[{}]的消息[{}]", session.getId(), message);
        Session toSession = clients.get("0");
        this.sendMessage(message, toSession);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 服务端发送消息给客户端
     */
    private void sendMessage(String message, Session toSession) {
        try {
            log.info("服务端给客户端[{}]发送消息[{}]", toSession.getId(), message);
            toSession.getBasicRemote().sendText(message);
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败：{}", e);
        }
    }

    @PostMapping("/get")
    public ResponseEntity<Object> getNotificationList(@RequestHeader("Authorization") String token) {
        Map<String, Claim> claimMap = JWTUtil.getClaimsFromHeader(token);
        ResultMap result = notificationService.getNotificationList(
                claimMap.get("restaurantId").asInt(),
                claimMap.get("position").asInt());
        return ResponseWrapper.wrap(result);
    }

    public Object broadcastMessage(Notification notification){
        return null;
    }
}