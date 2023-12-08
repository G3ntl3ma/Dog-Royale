package com.example.myapplication.messages.menu;


import lombok.Data;

/**
 * Error Message
 *
 * @author kellerb
 */
@Data
public class Error extends AbstractMenuMessage {

    private TypeMenu dataId;
    private String message;


}
