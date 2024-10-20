package com.bitcamp.jackpot.controller;

import com.bitcamp.jackpot.service.WidgetService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WidgetController {

    private final WidgetService widgetService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/confirm")
    public ResponseEntity<JSONObject> confirmPayment(@RequestBody String jsonBody) throws Exception {

        JSONParser parser = new JSONParser();
        JSONObject requestData = (JSONObject) parser.parse(jsonBody);

//        if(isFunding){
//
//        }else{
//            System.out.println( "여기부터 requestData 시작 : \n" + requestData.toJSONString());
//
//            String sProductNames = requestData.get("productNames") != null ? requestData.get("productNames").toString() : null;
//            System.out.println("여기부터 ProductNames 시작 : \n "+ sProductNames);
//
//            JSONArray productNames = (JSONArray) parser.parse(sProductNames);
//            for (Object o :  productNames){
//                JSONObject product = (JSONObject) parser.parse(o.toString());
//                System.out.println("여기부터 ProductNames 내부의 Object 시작 : \n "+ o.toString());
//                System.out.println("여기부터 ProductNames 내부의 Object의 shopId 시작 : \n "+ ((JSONObject)parser.parse(o.toString())).get("shopId").toString());
//                OrdersDTO.builder()
//                        .orderId(orderId)
//                        .shopId((Integer)product.get("shopId"))
//                        .quantity((Integer)product.get("quantity"))
//                        .totalPrice((Integer)product.get("totalPrice"))
//                        .memberId()
//                        .deliveryState(0)
//                        .build();
//
//            }
//        }

        // 결과 반환
        return widgetService.confirmTossPayments(requestData);
    }
}
