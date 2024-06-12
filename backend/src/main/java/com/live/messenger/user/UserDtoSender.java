package com.live.messenger.user;

import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object (DTO) for user sender information.
 * This class is used to encapsulate the sender's user data that is transferred between the client and server.
 */
public class UserDtoSender {

    @NotBlank
    private String firstName; // First name of the sender

    @NotBlank
    private String lastName; // Last name of the sender

    private String profilePicture; // URL or path to the sender's profile picture

    /**
     * Gets the first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name.
     *
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name.
     *
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the profile picture.
     *
     * @return the profile picture
     */
    public String getProfilePicture() {
        return profilePicture;
    }

    /**
     * Sets the profile picture.
     *
     * @param profilePicture the profile picture to set
     */
    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    /**
     * Returns a string representation of the UserDtoSender.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "UserDtoSender{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                '}';
    }
}
