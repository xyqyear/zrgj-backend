package zrgj.order_everyday.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import zrgj.order_everyday.entity.Vip;

import java.util.List;

@Mapper
@Repository
public interface VipMapper {
    Vip getVipInfoByTelephone(String telephone);

    List<Vip> getVipList();

    Integer addNewVip(Vip vip);

    Integer updateVip(Vip vip);

    Integer deleteVip(String telephone);
}
