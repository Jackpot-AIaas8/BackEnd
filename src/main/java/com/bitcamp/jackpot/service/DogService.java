package com.bitcamp.jackpot.service;

import com.bitcamp.jackpot.dto.*;

import java.util.List;

public interface DogService {

    DogDTO register(DogDTO dogDTO);

    int edit(DogDTO dogDTO);

    void remove(int dogId);

    DogResponseDTO findOne(int dogId);

    void addHeart(int dogId);

    void addFund(FundDTO fundDTO);

    Iterable<DogListDTO> dogList(DogListRequestDTO dogListRequestDTO);

    Long getTotalDogNum();

     public List<FundDTO> fundListMyPage();
}
