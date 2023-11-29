package com.nexusvision.messages.menu;
import java.util.ArrayList;

import lombok.Data;

@Data
public class ConnectToServer extends AbstractMenuMessage {
    private String name;
    private boolean isObserver;

    public String getResponse(ArrayList<Integer> Ids) {
        /*
	  {
	  " type " : TypeMenue . connectedToServer ,
	  " clientId " : int
	  }
	
        or
	
        error
         */
        String response = "connect to server fail (null)";

	if(name == null ) { //isObserver
	    return "{\"type\": " + (TypeMenue.error.ordinal() + 100) +
		", \"dataID\": " + TypeMenue.connectToServer.ordinal() + // + 100???
		", \"message\": \"could not connect to server because no name and observer bool given\"}";
	}
	
	//TODO add clientId to the "server" somehow
	int id = Ids.size();
	Ids.add(id);
	
	return "{\"type\": " + (TypeMenue.connectedToServer.ordinal() + 100) +
	    ", \"clientId\": " + id + "}";
	    
        // return response;
    }
}
