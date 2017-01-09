package com.example.administrator.icdsalesrep.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nitin on 24/08/16.
 */
public class CloudDocumentResponse {

    /*{
        "DataCollection": "OnX+OQKJEBbSU+Llh4ow1EdvjoICGmy8Lg4ocw18+1Nuuhq9K5zv4HLvineOna3kDIgdqFcI4Q69Iej5lAI\/ZCHzvPSgMvYxYbzZar\/ikDrOFAfHcPA0fGNdmqFaz3Sd8rbKxdwib2aEGfjbF7HG5j3YxVMifix4uaqmc33voclxTrNJQYGlMVtRpntHZdcznw19bnXoRkqg1dPaXSAm5Qt+fBxUekcCCHojDFuK679Lor3ptP7+YJwLNY98uyU8ccj1S9gxcHPB4WOffIJPzImRwZbtKfsFPs15H2\/BlTfB\/qmspjFbX+5cSVbMm4GHwhU8HwOj7rsuDA0iACPVl\/iqFAoK8rBP+JvlwjVIpBFY+KgnfU0uDSCCSUbTJrLmxVWARWanaKzP0L4isvVoA4k4rhxzCulbUBNMvEW9f0P3vuX4amjdSyNytwSPyXMT85vKuiOdL8SqUo2l7qDZT9jKgdliR1rdaYIlVs4c6oY0bmMrZajSKa\/kkOdiolCwW\/RpLQvCaVrqMuRQTJ8Kr3mFkczwmSCEmiiNpGY5hKIrAxtB5xdJX2bmcxJ\/kTZ\/3wG5ltXs\/700CGsro8j2SE2goHbg5saygsAzYWCoBu74hBGnBv7xmqE4269DVYseFjCKvQTEszFg6RG\/lWkYEJn2xveDA1+5MbWOwDeE+CUOG9cD\/mdgyHtot4p2P\/vzCkbG67pATO+8OxWxhcKCS9ihXaNuvQvoAz5G0ZvyjLDmt5pn3mPq6MiWhgyli6Pt\/0RM6rZ2\/+a5Z7Z\/VAwt1vRKp+VTs0Un9x2tItZu2lO+jlQLd\/9CpX1GEgjSSEIFxh2gkJPKbcw1FFetVo7oBRcR8AHAvwULHQFDYnwIX3dQhUDsnBbM+bJlhn3qqg6wLFH\/1vJZ3eExcXKBtg+szx9lJjDSlSsvyAQRV10dmL4M6FWGUqmqU1WnjXmobuA5Xw1VGFW6Ym9H\/3J2CI1PAeE5\/ptG4XFYYwlIm3cGQnRSOOegXmYKEdqBxe6Ivc+50AbZixzoh+xqTaPujBUY1Z4AsaQf+\/K0beIo8MaGWasUsfR1y6\/ayEUIqqK8KKw4QJCSXXUXloYoCeNTRV8YcjMvgQ6l1DX\/t8hH5gX0jfeCxb+pUet4A0oeSRBoymAY8tn0f1wEJ3SbvRzpdgTlqkYq3wn4OTG3aEhcSMHp8aqG1ljt\/5aBY6t7ufYK9dbe58JS9IzIwyVYVxgAe7QBXi7xm0jLKZAn1DhHm9UvDRF\/b07kyOV7BydhYJ4DPwHoqwOQC43p+JGKzFN0HBRHFUb1U\/Gbhrx+\/3k3isK527I0vl73ceQNxYXInzPJ3TeIqiO5FTMZ9UrkLWMGiisBBDOufmBW\/v2C9QmmzqojRRLVFg2S1AhjnTUgN3AVaNXGf0DVCA83hNJXAep4I19qPvAkqMEe2kd+\/hWhftLAqgalX21stx7dasNwuSXTBFu+PqVaadCcSMwV2fWgeax1VzC2ilfvB3MEIXgXNcj2xLxpat8VPpSLeQj4xwBi6xpfVyoQIKKVXUcyqzN1lryVvhji7M1ygNHMZZbvg5ZZ1gHFDvTkUoOFAOuksucH9as1yWPkFv21B3w7XJSA6QmgyBEtMEtey8C7gt8IEWSwmV59Qj01CHYM7E0joMGwFpEEFbUtaANZaBWHVPFIdTc+5A==",
            "Message": "Success",
            "ResponseCode": "200",
            "ServiceName": "OrdericdCloudDocument"
    }*/

    @SerializedName("DataCollection")
    public String dataCollection;
    @SerializedName("Message")
    public String message;
    @SerializedName("ResponseCode")
    public String responseCode;
    @SerializedName("ServiceName")
    public String serviceName;

    /*
    {"Documents":[{"DocumentName":"Deed of Trust.pdf","DocumentDownloadUrl":"http://dev.icdcloud.com/icdCloud/FileManagement_Handler/DownloadHandler/DownloadHandler.ashx?FileFolderGuid=9ae7a24e-890b-4541-b54f-fc18ee7d3679"},{"DocumentName":"Property Detail Report.pdf","DocumentDownloadUrl":"http://dev.icdcloud.com/icdCloud/FileManagement_Handler/DownloadHandler/DownloadHandler.ashx?FileFolderGuid=b0a37412-1558-4898-9afd-2d9b363f22db"},{"DocumentName":"T10175 Invoice.pdf","DocumentDownloadUrl":"http://dev.icdcloud.com/icdCloud/FileManagement_Handler/DownloadHandler/DownloadHandler.ashx?FileFolderGuid=3a4573ad-c564-4a96-8cf0-34fe3b4029ec"},{"DocumentName":"T10175 Policy.pdf","DocumentDownloadUrl":"http://dev.icdcloud.com/icdCloud/FileManagement_Handler/DownloadHandler/DownloadHandler.ashx?FileFolderGuid=f752ed2e-2d9c-4dab-bc4c-ac4f089fbf3b"},{"DocumentName":"T10175 Prelim.pdf","DocumentDownloadUrl":"http://dev.icdcloud.com/icdCloud/FileManagement_Handler/DownloadHandler/DownloadHandler.ashx?FileFolderGuid=4ae1b83d-a184-440b-ad50-d9a67302ab5b"},{"DocumentName":"WestHills CCR.pdf","DocumentDownloadUrl":"http://dev.icdcloud.com/icdCloud/FileManagement_Handler/DownloadHandler/DownloadHandler.ashx?FileFolderGuid=dd42a27d-f1df-411e-8e26-a32aba008787"}]}
     */
}
