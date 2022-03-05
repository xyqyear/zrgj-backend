package zrgj.order_everyday.controller;

import com.auth0.jwt.interfaces.Claim;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zrgj.order_everyday.entity.Notification;
import zrgj.order_everyday.pojo.dto.ResultMap;
import zrgj.order_everyday.service.NotificationService;
import zrgj.order_everyday.util.JWTUtil;
import zrgj.order_everyday.util.ResponseWrapper;

import java.util.Map;


@RequestMapping("/notification")
@RestController
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/get")
    public ResponseEntity<Object> getNotificationList(@RequestHeader("Authorization") String token) {
        Map<String, Claim> claimMap = JWTUtil.getClaimsFromHeader(token);
        ResultMap result = notificationService.getNotificationList(
                claimMap.get("restaurantId").asInt(),
                claimMap.get("position").asInt());
        return ResponseWrapper.wrap(result);
    }
    @PostMapping("/add")
    public ResponseEntity<Object> addNotification(@RequestBody Notification notification, @RequestHeader("Authorization") String token) {
        Map<String, Claim> claimMap = JWTUtil.getClaimsFromHeader(token);
        notification.setRestaurantId(claimMap.get("restaurantId").asInt());
        notification.setSenderId(claimMap.get("userId").asInt());
        ResultMap result = notificationService.addNotification(notification);
        return ResponseWrapper.wrap(result);
    }

    @PostMapping("/delete")
    @RequiresRoles("0")
    public ResponseEntity<Object> deleteNotification(@RequestBody Map<String, Integer> body, @RequestHeader("Authorization") String token) {
        ResultMap result = notificationService.deleteNotification(body.get("id"));
        return ResponseWrapper.wrap(result);
    }

    @PostMapping("/update")
    @RequiresRoles("0")
    public ResponseEntity<Object> updateNotification(@RequestBody Notification notification, @RequestHeader("Authorization") String token) {
        ResultMap result = notificationService.updateNotification(notification);
        return ResponseWrapper.wrap(result);
    }

    @PostMapping("/confirm")
    public ResponseEntity<Object> confirmNotification(@RequestBody Map<String, String> body, @RequestHeader("Authorization") String token) {
        Map<String, Claim> claimMap = JWTUtil.getClaimsFromHeader(token);
        Integer id = claimMap.get("userId").asInt();
        ResultMap result = notificationService.confirmNotification(Integer.parseInt(body.get("id")), id);
        return ResponseWrapper.wrap(result);
    }

}
