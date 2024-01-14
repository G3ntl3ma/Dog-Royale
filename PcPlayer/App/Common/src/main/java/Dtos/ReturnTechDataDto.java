package Dtos;

import Enums.TypeMenue;
import com.google.gson.Gson;

public class ReturnTechDataDto extends Dto {
    public final int type = TypeMenue.returnTechData.ordinal() + 100;
    private String serverVersion;
    private String supportedProtocol;
    private int[] embededExtensions;

    public ReturnTechDataDto(String serverVersion, String supportedProtocol, int[] embededExtensions){
        this.serverVersion = serverVersion;
        this.supportedProtocol = supportedProtocol;
        this.embededExtensions = embededExtensions;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getType() {
        return type;
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

    public int[] getEmbededExtensions() {
        return embededExtensions;
    }

    public void setEmbededExtensions(int[] embededExtensions) {
        this.embededExtensions = embededExtensions;
    }

}