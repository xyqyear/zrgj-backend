package zrgj.order_everyday.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zrgj.order_everyday.entity.Notification;
import zrgj.order_everyday.mapper.NotificationMapper;
import zrgj.order_everyday.pojo.dto.ResultMap;

@Service
public class NotificationService {
    
    @Autowired
    NotificationMapper notificationMapper;


    public ResultMap getNotificationList(Integer restaurantId, Integer position) {
        notificationMapper.getNotificationList(restaurantId, position);
    }

    public ResultMap addNotification(Notification notification) {
    }
}
