package zrgj.order_everyday.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zrgj.order_everyday.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    AccountService accountService;
}
