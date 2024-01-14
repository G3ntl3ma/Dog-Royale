package com.nexusvision.server.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nexusvision.server.model.messages.menu.Error;
import com.nexusvision.server.model.messages.menu.TypeMenue;
import com.nexusvision.utils.NewLineAppendingSerializer;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Abstract class implementing error handling
 *
 * @author felixwr
 */
@Log4j2
public abstract class Handler {
    protected static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Object.class, new NewLineAppendingSerializer<>())
            .create();
}
