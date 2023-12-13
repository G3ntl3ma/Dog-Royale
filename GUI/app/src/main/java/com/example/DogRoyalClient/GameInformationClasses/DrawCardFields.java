package com.example.DogRoyalClient.GameInformationClasses;

import java.util.List;

import lombok.Data;

@Data
public class DrawCardFields{
    private Integer count;
    private List<Integer> positions;
    public DrawCardFields(Integer count, List<Integer> positions){
        this.count = count;
        this.positions = positions;
    }

    public Integer getCount(){
        return count;
    }

    public List<Integer> getPositions(){
        return positions;
    }
}
