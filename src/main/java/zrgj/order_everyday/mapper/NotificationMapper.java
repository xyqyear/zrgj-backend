package zrgj.order_everyday.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import zrgj.order_everyday.entity.Notification;

import java.util.List;

@Mapper
@Repository
public interface NotificationMapper {

    List<Notification> getNotificationList(Integer restaurantId, Integer position);

    Integer deleteNotification(Integer id);

    Integer updateNotification(Notification notification);

    Integer addNotification(Notification notification);

    Integer confirmNotification(String sql);
}
