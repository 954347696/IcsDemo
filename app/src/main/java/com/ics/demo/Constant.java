package com.ics.demo;

import com.keepfun.aiservice.constants.YLConstant;

/**
 * @author yang
 * @description
 * @date 2020/10/22 10:11 AM
 */
public class Constant {

    public static String loginUrl = "https://saas.isaas.cyou/apis/";

    static {
        if (YLConstant.appMode == YLConstant.AppMode.Test) {
            loginUrl = "http://47.103.61.75/api-ls/";
        } else if(YLConstant.appMode == YLConstant.AppMode.Overseas_Test){
            loginUrl = "https://saastest.isaas.cyou/apis/";
        } else if(YLConstant.appMode == YLConstant.AppMode.Overseas){
//            loginUrl = "https://saas.isaas.cyou/apis/";
            loginUrl = "https://saastest.isaas.cyou/apis/";
        }
    }

    public static String appKey = "xUgYVZgj";
    public static String appSecret = "5d59d7930c20d059e74ae351fc5e4359f48bd649";
    public static String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKsY+ml2QaEyZ67hfzGv2lj/KZBuX9C6G4DrLgSz7Nebw/lQ8eDV/7Bm4z5pxnm2/kJZlhWoblrXgk40XaBrnNbjKyL9dvgGh0gOP/lwTfyHx1XbscjfIjtt/HsyBW+vkHkfeBiskcHwKd+KltBzuEQHJcC0BsZ5xMoAsR22vGJ9AgMBAAECgYAPAZ3U6h5PeRBG8lgpj3bjH14R4NxefjQzVm0u1GkNcAf8MfFE+v/2BNAhhT13J2mdgCX9uEwVg5lWKpmbnFShDzaTzd2cqatiLaqXkyvuDQj3hXBg63fuRgBIm99HUsh2J+mM5YKo2zSpZc0i5HT6gVoEzZrKAgqM+CyiLnXhsQJBAO+23iSR0K7fR2vP4TcOUF6XwL4jxYtXacNb/l2s92vFzlWq7YCXD1hjJoh23zYpOtiICttihbqSZ/RllKFJX6sCQQC2uLjNbjc6y0hhvEl+CUhawUiVGN0100rmXv2fy/I0ev4lNHWAcQ+ugv/NR6N/H4+UrAtf3/M3EO9gfPS54L53AkEA6HyZ3CvTkhaYxTZbPjNSvTs+wuocqDTCLael/qJHYK36nNlm+OUJC/c1ovpvJ687FFB0/ysBed6OldiDGx33ewJBAITLr5OtvHoAb7SIcSkmee1rd+LUVUDfNRpEgzN1t6uoj7hebd9RTEJadqqsnAix2VoFx6aATnUA29hWOE43JnUCQCvk6M+pDJmEAwKoahJdWOIpxz2vbD8/meifEksjY36g5cNJAPCIf7xLMxnhyikK5BpcFubifiIBTnW6kATw/gE=";
}
