package com.neeraj.urlshortener.util;

import org.springframework.stereotype.Component;

@Component
public class Base62Encoder {
    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public String encode(long id){
        StringBuilder sb = new StringBuilder();
        while(id > 0){
            sb.append(BASE62.charAt((int)(id % 62)));
            id /= 62;
        }
        return sb.reverse().toString();
    }

}
