package com.example.server;

import java.io.Serializable;

/**
 * This class is used as wrapper for data used to log in.
 */
public class AuthenticationData implements Serializable {
    public String login;
    public String password;
}
