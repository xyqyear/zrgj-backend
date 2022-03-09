package zrgj.order_everyday.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zrgj.order_everyday.entity.Vip;
import zrgj.order_everyday.mapper.VipMapper;
import zrgj.order_everyday.pojo.dto.ResultMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VipService {

    @Autowired
    VipMapper vipMapper;

    public ResultMap getVipInfoByTelephone(String telephone){
        Vip vip = vipMapper.getVipInfoByTelephone(telephone);
        if(vip == null){
            return ResultMap.failure("vip doesn't exist");
        }
        return ResultMap.success(vip);
    }

    public ResultMap getVipList(){
        List<Vip> vips = vipMapper.getVipList();
        return ResultMap.success(vips);
    }

    public ResultMap addNewVip(Vip vip){
        if(vipMapper.getVipInfoByTelephone(vip.getTelephone()) != null){
            return ResultMap.failure("this telephone already exists");
        }
        vipMapper.addNewVip(vip);
        return ResultMap.success(null);
    }

    public ResultMap updateVip(Vip vip){
        Vip member = vipMapper.getVipInfoByTelephone((vip.getTelephone()));
        if(member == null){
            return ResultMap.failure("vip doesn't exist");
        }
        if (vip.getScore() < 0) {
            return ResultMap.failure("invalid score");
        }
        vipMapper.updateVip(vip);
        return ResultMap.success(null);
    }

    public ResultMap deleteVip(String telephone){
        if(vipMapper.getVipInfoByTelephone(telephone) == null){
            return ResultMap.failure("vip doesn't exist");
        }
        vipMapper.deleteVip(telephone);
        return ResultMap.success(null);
    }
}
