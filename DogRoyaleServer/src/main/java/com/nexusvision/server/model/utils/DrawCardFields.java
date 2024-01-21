package com.nexusvision.server.model.utils;

import lombok.Data;

import java.util.List;

/**
 * @author felixwr
 */
@Data
public class DrawCardFields {
    private Integer count;
    private List<Integer> positions;
}