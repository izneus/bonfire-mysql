package com.izneus.bonfire.module.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author Izneus
 * @date 2020-07-20
 */
public class SecurityUser extends User {
    @Getter
    private String id;

    public SecurityUser(String userId, String username, String password,
                        Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = userId;
    }
}
