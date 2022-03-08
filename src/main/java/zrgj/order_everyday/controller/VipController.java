package zrgj.order_everyday.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zrgj.order_everyday.entity.Vip;
import zrgj.order_everyday.pojo.dto.ResultMap;
import zrgj.order_everyday.service.VipService;
import zrgj.order_everyday.util.ResponseWrapper;

import java.util.Map;

@RequestMapping(value = "/vip")
@RestController
public class VipController {

    @Autowired
    VipService vipService;

    @PostMapping(value = "/get")
    public ResponseEntity<Object> getVipInfoByTelephone(@RequestBody Map<String,String>body){
        ResultMap result = vipService.getVipInfoByTelephone(body.get("telephone"));
        return ResponseWrapper.wrap(result);
    }

    @PostMapping(value = "/list")
    public ResponseEntity<Object> getVipList(){
        ResultMap result = vipService.getVipList();
        return ResponseWrapper.wrap(result);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<Object> addNewVip(@RequestBody Vip vip){
        ResultMap result = vipService.addNewVip(vip);
        return ResponseWrapper.wrap(result);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> updateVip(@RequestBody Vip vip){
        ResultMap result = vipService.updateVip(vip);
        return ResponseWrapper.wrap(result);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<Object> deleteVip(@RequestBody Map<String,String>body){
        ResultMap result = vipService.deleteVip(body.get("telephone"));
        return ResponseWrapper.wrap(result);
    }
}
