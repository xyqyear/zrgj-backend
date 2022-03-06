package zrgj.order_everyday.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import zrgj.order_everyday.entity.Notification;
import zrgj.order_everyday.mapper.AccountMapper;
import zrgj.order_everyday.mapper.NotificationMapper;
import zrgj.order_everyday.pojo.dto.ResultMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotificationService {

    @Autowired
    NotificationMapper notificationMapper;

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    private SimpMessagingTemplate template;

    public ResultMap getNotificationList(Integer restaurantId, Integer position) {
        List<Notification> notifications = notificationMapper.getNotificationList(restaurantId, position);
        return ResultMap.success(notifications);
    }

    public ResultMap addNotification(Notification notification) {
        Map<Integer, Boolean> confirmation = new HashMap<>();
        notification.setCreateTime((int) (System.currentTimeMillis() / 1000));
        if (notification.getReceiverType() == null) {
            notification.setReceiverType(3);
        }
        List<Integer> targetPositions = new ArrayList<>();
        if (notification.getReceiverType() > 2) {
            targetPositions.add(0);
            targetPositions.add(1);
            targetPositions.add(2);
        } else {
            targetPositions.add(notification.getReceiverType());
        }
        for (int p : targetPositions) {
            List<Integer> list = accountMapper.getIdByPosition(notification.getRestaurantId(), p);
            for (Integer id : list) {
                confirmation.put(id, false);
            }
            this.template.convertAndSend("/notification/" + notification.getRestaurantId() + "/" + p, notification);
            System.out.println("/notification/" + notification.getRestaurantId() + "/" + p);
        }
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.valueToTree(confirmation);
        notification.setConfirmation(node);
        notificationMapper.addNotification(notification);
        return ResultMap.success(null);
    }

    public ResultMap deleteNotification(Integer id) {
        Integer deleteNum = notificationMapper.deleteNotification(id);
        if (deleteNum == 0) {
            return ResultMap.failure("notification id doesn't exist");
        }
        return ResultMap.success(null);
    }

    public ResultMap updateNotification(Notification notification) {
        notificationMapper.updateNotification(notification);
        return ResultMap.success(null);
    }

    public ResultMap confirmNotification(Integer notificationId, Integer id) {
        String sql =
                "update notification set confirmation = JSON_SET(confirmation, '$.\"" + id + "\"', true)where id =" + notificationId + " and deleted=false";
        notificationMapper.confirmNotification(sql);
        return ResultMap.success(null);
    }


}
