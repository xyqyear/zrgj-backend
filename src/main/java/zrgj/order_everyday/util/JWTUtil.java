package zrgj.order_everyday.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import zrgj.order_everyday.entity.Account;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 *
 * @Author yuanhaoyue swithaoy@gmail.com
 * @Description JWT 工具类
 * @Date 2018-04-07
 * @Time 22:48
 */
public class JWTUtil {
    // 过期时间 24 小时
    private static final long EXPIRE_TIME = 60 * 24 * 60 * 1000;
    // 密钥
    private static final String SECRET = "order_everyday_v1";

    /**
     * 生成 token, 24h后过期
     *
     * @param account 账户
     * @return 加密的token
     */
    public static String createToken(Account account) {
        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            // 附带username信息
            return JWT.create()
                    .withClaim("id", account.getId())
                    .withClaim("position", account.getPosition())
                    .withClaim("restaurantId", account.getRestaurantId())
                    .withClaim("userId", account.getId())
                    //到期时间
                    .withExpiresAt(date)
                    //创建一个新的JWT，并使用给定的算法进行标记
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            return "failure in creating jwt token";
        }
    }

    /**
     * 校验 token 是否正确
     *
     * @param token    密钥
     * @param info 用户名
     * @return 是否正确
     */
    public static boolean verify(String token, Map<String, Claim> info) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            //在token中附带了username信息
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("id", info.get("id").asInt())
                    .withClaim("position", info.get("position").asInt())
                    .withClaim("restaurantId", info.get("restaurantId").asInt())
                    .withClaim("userId", info.get("userId").asInt())
                    .build();
            //验证 token
            verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获得token中的信息，无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static Map<String, Claim> getClaims(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaims();
        } catch (JWTDecodeException e) {
            e.printStackTrace();
            return new HashMap<String, Claim>();
        }
    }

    /**
     * 获得token中的信息，无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static Map<String, Claim> getClaimsFromHeader(String token) {
        token = token.substring(7);
        return getClaims(token);
    }
}
