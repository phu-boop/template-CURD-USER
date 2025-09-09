package phunla2784.edu.vn.website.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PaymentService {

    private final String tmnCode;
    private final String hashSecret;
    private final String paymentUrl;
    private final String returnUrl;

    public PaymentService(
            @Value("${vnpay.tmnCode}") String tmnCode,
            @Value("${vnpay.hashSecret}") String hashSecret,
            @Value("${vnpay.paymentUrl}") String paymentUrl,
            @Value("${vnpay.returnUrl}") String returnUrl
    ) {
        this.tmnCode = tmnCode;
        this.hashSecret = hashSecret;
        this.paymentUrl = paymentUrl;
        this.returnUrl = returnUrl;
    }

    public String createPaymentUrl(Long orderId, Long amount) {
        Map<String, String> params = new HashMap<>();
        params.put("vnp_Version", "2.1.0");
        params.put("vnp_Command", "pay");
        params.put("vnp_TmnCode", tmnCode);
        params.put("vnp_Amount", String.valueOf(amount * 100)); // amount * 100 theo VNPAY
        params.put("vnp_CurrCode", "VND");
        params.put("vnp_TxnRef", orderId.toString());
        params.put("vnp_OrderInfo", "Thanh toán đơn hàng #" + orderId);
        params.put("vnp_ReturnUrl", returnUrl);
        params.put("vnp_CreateDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

        // sắp xếp key theo alphabet
        List<String> fieldNames = new ArrayList<>(params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        for (String key : fieldNames) {
            hashData.append(key).append("=").append(params.get(key)).append("&");
            query.append(URLEncoder.encode(key, StandardCharsets.US_ASCII))
                    .append("=")
                    .append(URLEncoder.encode(params.get(key), StandardCharsets.US_ASCII))
                    .append("&");
        }
        // bỏ ký tự '&' cuối cùng
        String queryUrl = query.substring(0, query.length() - 1);
        String data = hashData.substring(0, hashData.length() - 1);

        String vnp_SecureHash = hmacSHA512(hashSecret, data);
        return paymentUrl + "?" + queryUrl + "&vnp_SecureHash=" + vnp_SecureHash;
    }

    private String hmacSHA512(String key, String data) {
        try {
            Mac hmac = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            hmac.init(secretKey);
            byte[] bytes = hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hash = new StringBuilder();
            for (byte b : bytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hash.append('0');
                hash.append(hex);
            }
            return hash.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error while calculating HMAC", e);
        }
    }
}
