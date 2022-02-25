package zrgj.order_everyday.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zrgj.order_everyday.entity.Account;
import zrgj.order_everyday.mapper.AccountMapper;
import zrgj.order_everyday.pojo.dto.ResultMap;
import zrgj.order_everyday.util.Encryption;

@Service
public class AccountService {

    @Autowired
    AccountMapper accountMapper;

    public ResultMap authenticate(String username, String password){

        Account user = accountMapper.getAccountByUsername(username);
        if (user == null){
            return ResultMap.failure("invalid username");
        }
        String encryptedPassword = Encryption.getSHA256(password);
        if (! encryptedPassword.equals(user.getPassword())){
            return ResultMap.failure("wrong password");
        }
        user.setPassword("就不告诉你");
        return ResultMap.success(user);
    }

    public ResultMap addNewAccount(Account account) {
        Account user = accountMapper.getAccountByUsername(account.getUsername());
        if (user != null){
            return ResultMap.failure("username already exists");
        }
        accountMapper.addNewAccount(account);
        return ResultMap.success(null);
    }
}
