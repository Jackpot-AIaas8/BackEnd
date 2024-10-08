package com.bitcamp.jackpot.review;

import com.bitcamp.jackpot.domain.Member;
import com.bitcamp.jackpot.domain.Review;

import com.bitcamp.jackpot.domain.Shop;
import com.bitcamp.jackpot.dto.ReviewDTO;
import com.bitcamp.jackpot.repository.MemberRepository;
import com.bitcamp.jackpot.repository.ReviewRepository;
import com.bitcamp.jackpot.service.ReviewService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
@Log4j2
public class ReviewTests {
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    ReviewService reviewService;

    @Test
    public void testRegister() {
        Member member = Member.builder()
                .memberId(2)
                .address("강남")
                .pwd("1234")
                .phone("010-1111-1111")
                .name("김학현")
                .nickName("hak")
                .build();
        Shop shop = Shop.builder()
                .shopId(1)
                .category(0)
                .detail("바지")
                .price(1234)
                .name("바지")
                .build();

        for (int i = 1; i < 5; i++) {
            Review review = Review.builder()
                    .reviewId(i)
                    .content("review" + i)
                    .member(member)
                    .shop(shop)
                    .build();
            log.info(review);
            reviewRepository.save(review);
        }
    }

    @Test
    public void editReview() {
        log.info(reviewRepository.findById(5));
    }


}
