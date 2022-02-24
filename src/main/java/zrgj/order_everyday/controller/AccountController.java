package zrgj.order_everyday.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zrgj.order_everyday.service.AccountService;

import java.util.Map;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    AccountService accountService;

    @RequestMapping("/hello")
    public Object helloTest(Map<String, String> body){
        return "hello";
    }

    public ResponseEntity<Object> authenticate(Map<String, String> body){
        Map<String, Object> result = accountService.authenticate(body.get("username"), body.get("password"));
        int statusCode;
        if ((Boolean) result.get("success")) {
            statusCode = 200;
        }else {
            statusCode = 403;
        }
        return ResponseEntity.status(statusCode).body(result);
    }

}
