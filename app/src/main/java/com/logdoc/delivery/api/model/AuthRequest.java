package com.logdoc.delivery.api.model;

import android.support.annotation.NonNull;

public class AuthRequest {

    private final static String GRANT_TYPE = "password";
    @NonNull
    private String grant_type;
    @NonNull
    private String username;
    @NonNull
    private String password;

    public AuthRequest() {
    }

    public AuthRequest(@NonNull String username, @NonNull String password) {
        this.grant_type = GRANT_TYPE;
        this.username = username;
        this.password = password;
    }

    @NonNull
    public String getGrantType() {
        return grant_type;
    }

    public void setGrantType(@NonNull String grant_type) {
        this.grant_type = grant_type;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }
}
