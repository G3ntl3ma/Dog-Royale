package com.example.myapplication.messages.menu;

import lombok.Data;

@Data
public class ConnectToServer extends AbstractMenuMessage {
    private String name;
    private boolean isObserver;

    public String getResponse() {
        /*
        return
        {
        " type " : TypeMenue . connectToServer ,
        " name " : String ,
        " isObserver " : boolean
        }
	
        or
	
        error
         */
	
        String response = null;


	//TODO 

	if(response == null) {
	    response = "{\"type\": " + (TypeMenue.error.ordinal() + 100) +
		", \"dataID\": " + TypeMenue.connectToServer.ordinal() + // + 100???
		", \"message\": \"could not connect to server\"}";
	}
        return response;
    }
}
