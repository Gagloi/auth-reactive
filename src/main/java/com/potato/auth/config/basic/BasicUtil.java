package com.potato.auth.config.basic;

import org.springframework.stereotype.Component;

import java.util.Base64;

//@Component
public class BasicUtil {


    public String extractUsername(String authToken) {
        String key = new String(Base64.getDecoder().decode(authToken));
        return key.split(":")[0];
    }


}
