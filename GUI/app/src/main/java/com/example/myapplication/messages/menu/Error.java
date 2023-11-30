package com.example.myapplication.messages.menu;


import lombok.Data;

/**
 * Error Message
 *
 * @author kellerb
 */
@Data
public class Error extends AbstractMenuMessage {
    private TypeMenue dataId;
    private String message;

    // TODO: getResponse()-Methode schreiben
}
