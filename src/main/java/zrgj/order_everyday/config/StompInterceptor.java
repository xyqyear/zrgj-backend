package zrgj.order_everyday.config;

import java.util.Map;

import com.auth0.jwt.interfaces.Claim;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

import zrgj.order_everyday.util.JWTUtil;

public class StompInterceptor implements ChannelInterceptor {
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor= StompHeaderAccessor.wrap(message);
        if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
            Map<String, Claim> userInfo = JWTUtil.getClaims(accessor.getUser().getName());
            int position = userInfo.get("position").asInt();
            int restaurantId = userInfo.get("restaurantId").asInt();
            if (!accessor.getDestination().equals("/notification/" + restaurantId + "/" + position) && !accessor.getDestination().equals("/orders/" + restaurantId)) {
                return null;
            }
        }
        return message;
    }
}
