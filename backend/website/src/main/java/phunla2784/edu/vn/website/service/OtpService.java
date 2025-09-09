package phunla2784.edu.vn.website.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
public class OtpService {

    private final RedisTemplate<String, String> redisTemplate;

    public OtpService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveOtp(String email, String otp, long expiryMinutes) {
        redisTemplate.opsForValue().set("OTP:" + email, otp, Duration.ofMinutes(1));
    }

    public boolean validateOtp(String email, String otp) {
        String storedOtp = redisTemplate.opsForValue().get("OTP:" + email);
        return otp.equals(storedOtp);
    }

    public void removeOtp(String email) {
        redisTemplate.delete(email);
    }
}
