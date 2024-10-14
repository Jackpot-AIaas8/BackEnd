//package com.bitcamp.jackpot.controller;
//
//// toss 결제 컨트롤러
//
//import org.apache.tomcat.util.json.JSONParser;
//import org.apache.tomcat.util.json.ParseException;
//
//import org.json.JSONObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.nio.charset.StandardCharsets;
//import java.util.Base64;
//
//@RestController
//@RequestMapping("/api")
//public class WidgetController {
//
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @PostMapping("/confirm")
//    public ResponseEntity<JSONObject> confirmPayment(@RequestBody String jsonBody) throws Exception {
//
//        JSONParser parser = new JSONParser();
//        String orderId;
//        String amount;
//        String paymentKey;
//        try{
//            // 클라이언트에서 받은 JSON 요청 바디를 파싱
//            JSONObject requestData = (JSONObject) parser.parse(jsonBody);
//            paymentKey = (String) requestData.get("paymentKey");
//            orderId = (String) requestData.get("orderId");
//            amount = (String) requestData.get("amount");
//        }catch (ParseException e){
//            throw new RuntimeException(e);
//        }
//        JSONObject  obj = new JSONObject();
//        obj.put("orderId", orderId);
//        obj.put("amount", amount);
//        obj.put("paymentKey", paymentKey);
//
//        // 토스페이먼츠 API 시크릿 키 설정
//        String widgetSecretKey = "test_gsk_docs_OaPz8L5KdmQXkzRz3y47BMw6";
//        Base64.Encoder encoder = Base64.getEncoder();
//        byte[] encodedBytes = encoder.encode((widgetSecretKey + ":").getBytes(StandardCharsets.UTF_8));
//        String authorization = "Basic" + new String(encodedBytes);
//
//        URL url = new URL("https://api.tosspayments.com/b1/payments/comfirm");
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//    }
//
//
//
//}
