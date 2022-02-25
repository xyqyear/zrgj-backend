package zrgj.order_everyday.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import zrgj.order_everyday.entity.Account;
import zrgj.order_everyday.pojo.dto.ResultMap;
import zrgj.order_everyday.service.AccountService;
import zrgj.order_everyday.util.ResponseWrapper;

import java.util.Map;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    AccountService accountService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public Object helloTest(Map<String, String> body) {
        System.out.println("hello world");
        return "hello";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Object> authenticate(Map<String, String> body) {
        ResultMap result = accountService.authenticate(body.get("username"), body.get("password"));
        return ResponseWrapper.wrap(result);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> addNewAccount(@RequestBody Account account) {
        return ResponseWrapper.wrap(accountService.addNewAccount(account));
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteAccount(@RequestBody Account account) {
        // TODO:
        return null;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Object> updateAccount(@RequestBody Account account) {
        // TODO:
        return null;

    }

    @RequestMapping(value ="/change_password", method = RequestMethod.DELETE)
    public ResponseEntity<Object> changePassword(@RequestBody Account account) {
        // TODO:
        return null;

    }
}
