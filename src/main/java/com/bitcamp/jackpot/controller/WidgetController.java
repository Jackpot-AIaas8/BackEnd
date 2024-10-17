package com.bitcamp.jackpot.controller;

import com.bitcamp.jackpot.dto.OrdersDTO;
import com.bitcamp.jackpot.dto.ProductDTO;
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
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api")
public class WidgetController {

    @Autowired
    private OrdersService ordersService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/confirm")
    public ResponseEntity<JSONObject> confirmPayment(@RequestBody String jsonBody) throws Exception {
        logger.info("결제 확인 요청 수신: {}", jsonBody);


        JSONParser parser = new JSONParser();
        String orderId;
        String amount;
        String paymentKey;
        String name;
        String phone;
        String address;
        int shopId;
        int memberID;


        try {
            // 클라이언트에서 받은 JSON 요청 바디를 파싱
            JSONObject requestData = (JSONObject) parser.parse(jsonBody);

            paymentKey = requestData.get("paymentKey") != null ? requestData.get("paymentKey").toString() : null;
            orderId = requestData.get("orderId") != null ? requestData.get("orderId").toString() : null;
            amount = requestData.get("amount") != null ? requestData.get("amount").toString() : null;
            name = requestData.get("name") != null ? requestData.get("name").toString() : null;
            phone = requestData.get("phone") != null ? requestData.get("phone").toString() : null;
            address = requestData.get("address") != null ? requestData.get("address").toString() : null;
            shopId = requestData.get("shopId") != null ? Integer.parseInt(requestData.get("shopId").toString()) : 0;
            memberID = requestData.get("memberID") != null ? Integer.parseInt(requestData.get("memberID").toString()) : 0;

            // memberId, shopId 등의 필수 값을 체크하는 로직 추가 가능
            if (memberID == 0 || shopId == 0) {
                throw new IllegalArgumentException("memberId 또는 shopId 값이 유효하지 않습니다.");
            }

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        // API 요청에 사용할 JSON 데이터 생성
        JSONObject obj = new JSONObject();
        obj.put("orderId", orderId);
        obj.put("amount", amount);
        obj.put("paymentKey", paymentKey);

        // 토스페이먼츠 API 시크릿 키 설정
        String widgetSecretKey = "test_gsk_docs_OaPz8L5KdmQXkzRz3y47BMw6";
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode((widgetSecretKey + ":").getBytes(StandardCharsets.UTF_8));
        String authorization = "Basic " + new String(encodedBytes);  // "Basic" 뒤에 공백 추가

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
        outputStream.close();  // 스트림 닫기

        // 응답 코드 확인
        int code = connection.getResponseCode();
        boolean isSuccess = code == 200;

        InputStream responseStream = isSuccess ? connection.getInputStream() : connection.getErrorStream();
        Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
        JSONObject jsonObject = (JSONObject) parser.parse(reader);
        responseStream.close();

        if (isSuccess) {
            OrdersDTO ordersDTO = new OrdersDTO();
            ordersDTO.setOrderId(orderId);
            ordersDTO.setTotalPrice(Integer.parseInt(amount));
            ordersDTO.setName(name);
            ordersDTO.setPhone(phone);
            ordersDTO.setAddress(address);
            ordersDTO.setMemberID(memberID);

            logger.info("Order ID: {}", ordersDTO.getOrderId());
            logger.info("Member ID: {}", ordersDTO.getMemberID());
            logger.info("Name: {}", ordersDTO.getName());
            logger.info("Total Price: {}", ordersDTO.getTotalPrice());

            List<ProductDTO> productList = new ArrayList<>();
            ProductDTO product = new ProductDTO();
            product.setShopId(shopId);
            product.setShopName("Example Shop");
            product.setProductPrice(10000);
            product.setQuantity(1);
            productList.add(product);

            ordersDTO.setProducts(productList);

            // 서비스로 넘겨 주문 등록 처리
            ordersService.register(ordersDTO);
        }

        // 결과 반환
        return ResponseEntity.status(code).body(jsonObject);
    }
}
