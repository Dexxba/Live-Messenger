package com.live.messenger.login;

import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object (DTO) for login requests.
 * This class is used to encapsulate the login credentials sent by the client.
 */
public class LoginDto {

    @NotBlank
    private String username; // The username for login

    @NotBlank
    private String password; // The password for login

    /**
     * Constructor to initialize the LoginDto with provided username and password.
     *
     * @param username the username for login
     * @param password the password for login
     */
    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns a string representation of the LoginDto.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "LoginDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
