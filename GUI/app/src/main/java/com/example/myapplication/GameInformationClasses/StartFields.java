package com.example.myapplication.GameInformationClasses;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class StartFields{
    private Integer count;
    private List<Integer> positions;

    public StartFields(Integer count, List<Integer> positions){
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