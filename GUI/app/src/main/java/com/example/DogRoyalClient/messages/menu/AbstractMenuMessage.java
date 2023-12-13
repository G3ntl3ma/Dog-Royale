package com.example.DogRoyalClient.messages.menu;

import lombok.Data;


/**
 * @author felixwr
 */
@Data
public abstract class AbstractMenuMessage {

    protected Integer type;


    public void setType(int ordinal) {
        this.type = ordinal;
    }
}
