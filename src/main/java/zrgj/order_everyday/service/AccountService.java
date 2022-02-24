package zrgj.order_everyday.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zrgj.order_everyday.entity.Account;
import zrgj.order_everyday.mapper.AccountMapper;
import zrgj.order_everyday.util.Encryption;

import java.util.HashMap;
import java.util.Map;

@Service
public class AccountService {

    @Autowired
    AccountMapper accountMapper;

    public Map<String,Object> authenticate(String username, String password){
        Map<String, Object> result = new HashMap<>();
        try {
            Account user = accountMapper.getAccountByUsername(username);
            if (user == null){
                throw new IllegalAccessException("invalid username");
            }
            String encryptedPassword = Encryption.getSHA256(password);
            if (! encryptedPassword.equals(user.getPassword())){
                throw new IllegalAccessException("wrong passowrd");
            }
            user.setPassword("就不告诉你");
            result.put("success", true);
            result.put("data", user);
        }catch (Exception e){
            result.put("success", true);
            result.put("reason", e.getMessage());
        }
        return result;
    }
}
