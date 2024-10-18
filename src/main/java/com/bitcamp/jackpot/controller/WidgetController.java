package com.bitcamp.jackpot.controller;

import com.bitcamp.jackpot.dto.OrdersDTO;
import com.bitcamp.jackpot.service.OrdersService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequestMapping("/api")
public class WidgetController {

    @Autowired
    private OrdersService ordersService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/confirm")
    public ResponseEntity<JSONObject> confirmPayment(@RequestBody String jsonBody) throws Exception {

        JSONParser parser = new JSONParser();
        String orderId;
        String paymentKey;
        int amount;
        boolean isFunding = false; // 펀딩 여부 추가

        try {
            // 클라이언트에서 받은 JSON 요청 바디를 파싱
            JSONObject requestData = (JSONObject) parser.parse(jsonBody);
            paymentKey = requestData.get("paymentKey") != null ? requestData.get("paymentKey").toString() : null;
            orderId = requestData.get("orderId") != null ? requestData.get("orderId").toString() : null;
            amount = requestData.get("amount") != null ? Integer.parseInt(requestData.get("amount").toString()) : 0;
            isFunding = requestData.get("isFunding") != null && (boolean) requestData.get("isFunding");

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        // API 요청에 사용할 JSON 데이터 생성
        JSONObject obj = new JSONObject();
        obj.put("orderId", orderId);
        obj.put("amount", amount);
        obj.put("paymentKey", paymentKey);
        obj.put("isFunding", isFunding); // 펀딩 여부 추가

        // 토스페이먼츠 API 시크릿 키 설정
        String widgetSecretKey = "test_gsk_docs_OaPz8L5KdmQXkzRz3y47BMw6";
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode((widgetSecretKey + ":").getBytes(StandardCharsets.UTF_8));
        String authorization = "Basic " + new String(encodedBytes);

        URL url = new URL("https://api.tosspayments.com/v1/payments/confirm");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization", authorization);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        // API 요청 바디 전송
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(obj.toString().getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();

        // 응답 코드 확인
        int code = connection.getResponseCode();
        boolean isSuccess = code == 200;

        InputStream responseStream = isSuccess ? connection.getInputStream() : connection.getErrorStream();
        Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
        JSONObject jsonObject = (JSONObject) parser.parse(reader);
        responseStream.close();

        // 결과 반환
        return ResponseEntity.status(code).body(jsonObject);
    }
}
