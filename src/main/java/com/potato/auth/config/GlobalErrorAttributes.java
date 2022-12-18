package com.potato.auth.config;

import com.potato.auth.exceptions.RestException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> map = super.getErrorAttributes(request, options);
        Throwable error = getError(request);
        if (error instanceof RestException) {
            map.put("status", ((RestException) error).getStatus());
            map.put("message", error.getMessage());
            return map;
        }
        map.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
        map.put("message", error.getMessage());
        return map;
    }

}