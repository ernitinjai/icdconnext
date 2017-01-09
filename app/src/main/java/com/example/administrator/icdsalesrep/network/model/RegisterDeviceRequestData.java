package com.example.administrator.icdsalesrep.network.model;

/**
 * Created by nitin on 22/08/16.
 */
public class RegisterDeviceRequestData {

    String CompanyCode;
    String DeviceID;

    public String getCompanyCode() {
        return CompanyCode;
    }

    public void setCompanyCode(String companyCode) {
        CompanyCode = companyCode;
    }

    public String getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(String deviceID) {
        DeviceID = deviceID;
    }
}
