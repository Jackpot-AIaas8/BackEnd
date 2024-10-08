package com.bitcamp.jackpot.controller;

import com.bitcamp.jackpot.dto.*;
import com.bitcamp.jackpot.service.DogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/dog")
@RequiredArgsConstructor
public class DogController {

    private final DogService dogService;

    @PostMapping("/register")
    public ResponseEntity<Integer> register(@RequestBody DogDTO dogDTO) {
        DogDTO savedDTO = dogService.register(dogDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedDTO.getDogId());
    }

    @PostMapping("/edit")
    public ResponseEntity<Integer> edit(@RequestBody DogDTO dogDTO) {
        if (dogService.edit(dogDTO) == 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(dogDTO.getDogId());
        }
    }

    @PostMapping("/remove")
    public void remove(@RequestParam int dogId) {
        dogService.remove(dogId);
    }

    @GetMapping("/findOne")
    public DogResponseDTO findOne(@RequestParam int dogId) {

        return dogService.findOne(dogId);
    }

    @PostMapping("/addHeart")

    public void addHeart(@RequestBody Map<String,Integer> dogId) {
        
       dogService.addHeart(dogId.get("dogId"));
    }

    @PostMapping("/addFund")
    public void addFund(@RequestBody FundDTO fundDTO) {
//        dogService.addFund(fundDTO); 안돼서 주석갈김 ㅅㄱ
    }

    @GetMapping("/dogList")
    public Map<String, Object> dogList(@RequestParam int page, int size, String sort) {
        Map<String, Object> response = new HashMap<>();
        System.out.println(page);
        System.out.println(size);
        System.out.println(sort);
        DogListRequestDTO dogListRequestDTO = DogListRequestDTO.builder()
                .page(page)
                .size(size)
                .sort(sort)
                .build();

        response.put("totalDogNum", dogService.getTotalDogNum());
        response.put("dogList", dogService.dogList(dogListRequestDTO));

        return response;
    }
}
