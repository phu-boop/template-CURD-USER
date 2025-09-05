package phunla2784.edu.vn.website.security;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class TokenBlacklistRedis {

    private final RedisTemplate<String, String> redisTemplate;

    public TokenBlacklistRedis(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // Lưu token vào Redis với TTL
    public void addToken(String token, long expirationSeconds) {
        redisTemplate.opsForValue().set(token, "logout", Duration.ofSeconds(expirationSeconds));
    }

    // Kiểm tra token có bị blacklist không
    public boolean contains(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(token));
    }
}
