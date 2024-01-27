package Dtos;

import Enums.TypeMenue;
import com.google.gson.Gson;

public class ReturnTechDataDto extends Dto {
    private String serverVersion;
    private String supportedProtocol;
    private int[] embeddedExtensions;

    public ReturnTechDataDto(String serverVersion, String supportedProtocol, int[] embeddedExtensions){
        super(TypeMenue.returnTechData.ordinal() + 100);
        this.serverVersion = serverVersion;
        this.supportedProtocol = supportedProtocol;
        this.embeddedExtensions = embeddedExtensions;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public String getServerVersion() {
        return serverVersion;
    }

    public void setServerVersion(String serverVersion) {
        this.serverVersion = serverVersion;
    }

    public String getSupportedProtocol() {
        return supportedProtocol;
    }

    public void setSupportedProtocol(String supportedProtocol) {
        this.supportedProtocol = supportedProtocol;
    }

    public int[] getEmbeddedExtensions() {
        return embeddedExtensions;
    }

    public void setEmbeddedExtensions(int[] embeddedExtensions) {
        this.embeddedExtensions = embeddedExtensions;
    }

}