package com.example.demo.domain;

import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN, USER;


    @Override
    public String getAuthority() {
        return name();
    }
}
