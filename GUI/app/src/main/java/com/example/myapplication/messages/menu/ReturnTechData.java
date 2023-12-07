package com.example.myapplication.messages.menu;

import lombok.Data;

import java.util.List;

/**
 * Server antwortet mit technischen Daten
 *
 * @author kellerb
 */
@Data
public class ReturnTechData extends AbstractMenuMessage{
    private String serverVersion;
    private String supportedProtocol;
    private List<Integer> embeddedExtensions;
}
