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
        String name;  // 구매자 이름
        String phone; // 전화번호
        String address; // 배송지 주소
        int shopId; // shopId 추가

        try {
            // 클라이언트에서 받은 JSON 요청 바디를 파싱
            JSONObject requestData = (JSONObject) parser.parse(jsonBody);
            paymentKey = (String) requestData.get("paymentKey");
            orderId = (String) requestData.get("orderId");
            amount = (String) requestData.get("amount");
            name = (String) requestData.get("name");   // name 필드 추가
            phone = (String) requestData.get("phone"); // phone 필드 추가
            address = (String) requestData.get("address"); // address 필드 추가
            shopId = Integer.parseInt(requestData.get("shopId").toString());
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

        // 결제 성공 시 주문 정보를 저장
        if (isSuccess) {
            OrdersDTO ordersDTO = new OrdersDTO();
            ordersDTO.setOrderId(orderId);  // 주문 ID 설정
            ordersDTO.setTotalPrice(Integer.parseInt(amount));  // 총 결제 금액 설정
            ordersDTO.setName(name);  // 구매자 이름 설정
            ordersDTO.setPhone(phone);  // 전화번호 설정
            ordersDTO.setAddress(address);  // 배송지 주소 설정

            // 상품 리스트 추가
            ProductDTO product = new ProductDTO();
            product.setShopId(shopId); // shopId 설정
            product.setShopName("Example Shop");
            product.setProductPrice(10000);  // 예시 가격
            product.setQuantity(1);  // 예시 수량

            List<ProductDTO> productList = new ArrayList<>();
            productList.add(product);  // 리스트에 상품 추가
            ordersDTO.setProducts(productList);  // OrdersDTO에 상품 리스트 설정

            // 주문을 저장하는 서비스 호출
            ordersService.register(ordersDTO);
        }

        // 결과 반환
        return ResponseEntity.status(code).body(jsonObject);
    }
}
