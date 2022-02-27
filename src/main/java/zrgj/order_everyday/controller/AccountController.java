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

    @PostMapping("/hello")
    public Object helloTest(@RequestBody Map<String, String> body) {
        System.out.println("hello world");
        return "hello";
    }

    @PostMapping("/login")
    public ResponseEntity<Object> authenticate(@RequestBody Map<String, Object> body) {
        ResultMap result = accountService.authenticate((Integer) body.get("id"), (String) body.get("password"));
        return ResponseWrapper.wrap(result);
    }

    @PostMapping("/add")
    @RequiresRoles("0")
    public ResponseEntity<Object> addNewAccount(@RequestBody Account account,
            @RequestHeader("Authorization") String token) {
        account.setRestaurantId(JWTUtil.getClaimsFromHeader(token).get("restaurantId").asInt());
        return ResponseWrapper.wrap(accountService.addNewAccount(account));
    }

    @PostMapping("/delete")
    @RequiresRoles("0")
    public ResponseEntity<Object> deleteAccount(@RequestBody Map<String, Integer> body) {
        return ResponseWrapper.wrap(accountService.deleteAccount(body.get("id")));
    }

    @PostMapping("/update")
    @RequiresRoles("0")
    public ResponseEntity<Object> updateAccount(@RequestBody Account account) {
        return ResponseWrapper.wrap(accountService.updateAccount(account));
    }

    @RequestMapping("/change_password")
    @RequiresRoles("0")
    public ResponseEntity<Object> changePassword(@RequestBody Account account) {
        return ResponseWrapper.wrap(accountService.changePassword(account));
    }

    @PostMapping("/get")
    public ResponseEntity<Object> getAccountInfo(@RequestBody Map<String, Integer> body) {
        return ResponseWrapper.wrap(accountService.getAccountInfo(body.get("id")));
    }

    @PostMapping("/list")
    @RequiresRoles("0")
    public ResponseEntity<Object> getAccountList(@RequestHeader("Authorization") String token) {
        Integer restaurantId = JWTUtil.getClaimsFromHeader(token).get("restaurantId").asInt();
        return ResponseWrapper.wrap(accountService.getAccountList(restaurantId));
    }
}
