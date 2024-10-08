package com.bitcamp.jackpot.ShopPage;

import com.bitcamp.jackpot.dto.ShopDTO;
import com.bitcamp.jackpot.repository.ShopRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class ShopDummyDataGenerateTest {

    @Autowired
    private ShopRepository shopRepository;

//    @Test
//    void ShopDummyDataGenerateRegister() {
//
//        // 더미 데이터 생성
//        for (int i = 1; i <= 20; i++) {
//            ShopDTO shopDTO = ShopDTO.builder()
//                    .shopId(i)
//                    .name("test product" + i)
//                    .category(i % 5 + 1) // 카테고리 1~5로 나눠 설정
//                    .price("10000" + i) // 가격 설정
//                    .detail("test detail" + i) // 상세 설명 추가
//                    .build();
//
//            shopRepository.save(shopDTO);
//        }
//    }
}
