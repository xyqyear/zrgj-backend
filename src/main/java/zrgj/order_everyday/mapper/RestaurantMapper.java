package zrgj.order_everyday.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import zrgj.order_everyday.entity.Restaurant;

@Mapper
@Repository
public interface RestaurantMapper {
    @Select("SELECT * FROM `restaurant` WHERE `id` = #{id}")
    Restaurant getRestaurantById(Integer id);

    @Update("UPDATE `restaurant` SET `name` = #{name}, `address` = #{address}, `telephone` = #{telephone} WHERE `id` = #{id}")
    void updateRestaurant(Integer id, String name, String address, String telephone);
}
