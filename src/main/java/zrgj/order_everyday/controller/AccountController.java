package zrgj.order_everyday.controller;


import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zrgj.order_everyday.entity.Account;
import zrgj.order_everyday.pojo.dto.ResultMap;
import zrgj.order_everyday.service.AccountService;
import zrgj.order_everyday.util.JWTUtil;
import zrgj.order_everyday.util.ResponseWrapper;

import java.util.Map;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    AccountService accountService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public Object helloTest(@RequestBody Map<String, String> body) {
        System.out.println("hello world");
        return "hello";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Object> authenticate(@RequestBody Map<String, String> body) {
        ResultMap result = accountService.authenticate(body.get("username"), body.get("password"));
        return ResponseWrapper.wrap(result);
    }

    @RequestMapping(method = RequestMethod.POST)
    @RequiresRoles("0")
    public ResponseEntity<Object> addNewAccount(@RequestBody Account account,
                                                @RequestHeader("Authorization") String token) {
        account.setRestaurantId(JWTUtil.getClaimsFromHeader(token).get("restaurantId").asInt());
        return ResponseWrapper.wrap(accountService.addNewAccount(account));
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @RequiresRoles("0")
    public ResponseEntity<Object> deleteAccount(@RequestBody Map<String, String> body) {
        return ResponseWrapper.wrap(accountService.deleteAccount(body.get("username")));
    }

    @RequestMapping(method = RequestMethod.PUT)
    @RequiresRoles("0")
    public ResponseEntity<Object> updateAccount(@RequestBody Account account) {
        return ResponseWrapper.wrap(accountService.updateAccount(account));
    }

    @RequestMapping(value ="/change_password", method = RequestMethod.PUT)
    @RequiresRoles("0")
    public ResponseEntity<Object> changePassword(@RequestBody Account account) {
        return ResponseWrapper.wrap(accountService.changePassword(account));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> getAccountInfo(@RequestBody Map<String, String> body) {
        return ResponseWrapper.wrap(accountService.getAccountInfo(body.get("username")));
    }

    @RequestMapping(value ="/list",method = RequestMethod.GET)
    @RequiresRoles("0")
    public ResponseEntity<Object> getAccountList(@RequestHeader("Authorization") String token) {
        Integer restaurantId = JWTUtil.getClaimsFromHeader(token).get("restaurantId").asInt();
        return ResponseWrapper.wrap(accountService.getAccountList(restaurantId));
    }
}
