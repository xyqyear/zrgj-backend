package zrgj.order_everyday.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import zrgj.order_everyday.entity.Dish;

import java.util.List;

@Mapper
@Repository
public interface DishMapper {

    Integer addNewDish(Dish dish);

    Integer deleteDish(String id);

    Integer updateDish(Dish dish);

    List<Dish> getDishList(Integer restaurantId);

    Dish getDishByName(Dish dish);

    Dish getDishById(Integer id);
}
