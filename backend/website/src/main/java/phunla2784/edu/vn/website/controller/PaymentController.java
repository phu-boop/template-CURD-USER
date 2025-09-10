package phunla2784.edu.vn.website.controller;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import phunla2784.edu.vn.website.service.PaymentService;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/pay")
    public void pay(HttpServletResponse response, @RequestParam Long orderId, @RequestParam Long amount) throws IOException {
        String url = paymentService.createPaymentUrl(orderId, amount);
        System.out.println("1");
        response.sendRedirect(url);
    }

    @GetMapping("/return")
    public String paymentReturn(@RequestParam Map<String, String> params) {
        String vnp_ResponseCode = params.get("vnp_ResponseCode");
        if ("00".equals(vnp_ResponseCode)) {
            return "Thanh toán thành công!";
        } else {
            return "Thanh toán thất bại!";
        }
    }
}
