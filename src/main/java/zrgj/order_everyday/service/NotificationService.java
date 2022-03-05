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
        List<Notification>notifications = notificationMapper.getNotificationList(restaurantId, position);
        return ResultMap.success(notifications);
    }

    public ResultMap addNotification(Notification notification) {
        Map<Integer, Boolean> confirmation =new HashMap<>();
        notification.setCreateTime((int) (System.currentTimeMillis() / 1000));
        notification.setReceiverType(1);
        List<Integer>list = accountMapper.getIdByPosition(notification.getRestaurantId(),notification.getReceiverType());
        for(Integer id : list){
            confirmation.put(id,false);
        }
        ObjectMapper mapper= new ObjectMapper();
        JsonNode node = mapper.valueToTree(confirmation);
        notification.setConfirmation(node);
        notificationMapper.addNotification(notification);
        // this.template.convertAndSend("/notification/1/0", "{\"id\": " + id + "}");
        return ResultMap.success(null);
    }

    public ResultMap deleteNotification(Integer id){
        Integer deleteNum = notificationMapper.deleteNotification(id);
        if (deleteNum == 0) {
            return ResultMap.failure("notification id doesn't exist");
        }
        return ResultMap.success(null);
    }

    public ResultMap updateNotification(Notification notification){
        notificationMapper.updateNotification(notification);
        return ResultMap.success(null);
    }

    public ResultMap confirmNotification(Integer notificationId,Integer id){
        String sql = "update notification set confirmation = JSON_SET(confirmation, '$.\""+id+"\"', true)where id ="+notificationId+" and deleted=false";
        notificationMapper.confirmNotification(sql);
        return ResultMap.success(null);
    }


}
