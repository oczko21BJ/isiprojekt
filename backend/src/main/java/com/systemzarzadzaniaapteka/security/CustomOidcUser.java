package com.systemzarzadzaniaapteka.security;

import com.systemzarzadzaniaapteka.model.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import java.util.Collection;

/**
 * Klasa reprezentująca niestandardowego użytkownika OIDC.
 * Rozszerza domyślną implementację użytkownika OIDC o dodatkowe informacje specyficzne dla aplikacji,
 * takie jak dane użytkownika z bazy danych.
 */
public class CustomOidcUser extends DefaultOidcUser {

    private final AppUser appUser;

    public CustomOidcUser(AppUser appUser, Collection<? extends GrantedAuthority> authorities,
                          OidcIdToken idToken, OidcUserInfo userInfo) {
        super(authorities, idToken, userInfo);
        this.appUser = appUser;
    }

    public AppUser getAppUser() {
        return appUser;
    }
}
