package com.live.messenger.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Service class for handling user-related operations.
 */
@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository; // Repository for accessing user data

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(); // Encoder for hashing passwords

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user
     * @return the Users object if found, or null if not found
     */
    public Users getUserById(int id) {
        return usersRepository.findById(id).orElse(null); // Find user by ID or return null if not found
    }

    /**
     * Retrieves a UserDto by user ID.
     *
     * @param id the ID of the user
     * @return the UserDto if found, or null if not found
     */
    public UserDto getUserDto(Integer id) {
        Users user = usersRepository.findById(id).orElse(null); // Find user by ID or return null if not found
        if (user == null) {
            return null; // Return null if user is not found
        }
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername()); // Set username in DTO
        userDto.setPassword(user.getPassword()); // Set password in DTO
        userDto.setEmail(user.getEmail()); // Set email in DTO
        userDto.setFirstName(user.getFirstName()); // Set first name in DTO
        userDto.setLastName(user.getLastName()); // Set last name in DTO
        userDto.setProfilePicture(user.getProfilePicture()); // Set profile picture in DTO
        return userDto; // Return the populated DTO
    }

    /**
     * Creates a new user.
     *
     * @param userDto the user data transfer object containing the user details
     * @return the created Users object
     */
    public Users createUser(UserDto userDto) {
        Users newUser = new Users(); // Create a new Users object
        return saveUser(userDto, newUser); // Save and return the new user
    }

    /**
     * Updates an existing user.
     *
     * @param id      the ID of the user to update
     * @param userDto the user data transfer object containing the updated user details
     * @return the updated Users object, or null if the user is not found
     */
    public Users updateUser(int id, UserDto userDto) {
        Users existingUser = usersRepository.findById(id).orElse(null); // Find user by ID or return null if not found
        if (existingUser == null) {
            return null; // Return null if user is not found
        }
        return saveUser(userDto, existingUser); // Save and return the updated user
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete
     */
    public void deleteUser(int id) {
        usersRepository.deleteById(id); // Delete user by ID
    }

    /**
     * Saves a user.
     *
     * @param userDto      the user data transfer object containing the user details
     * @param existingUser the Users object to update
     * @return the saved Users object
     */
    public Users saveUser(UserDto userDto, Users existingUser) {
        if (userDto.getUsername() != null) {
            existingUser.setUsername(userDto.getUsername()); // Set username if provided
        }
        if (userDto.getPassword() != null) {
            existingUser.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword())); // Hash and set password if provided
        }
        existingUser.setEmail(userDto.getEmail()); // Set email
        existingUser.setFirstName(userDto.getFirstName()); // Set first name
        existingUser.setLastName(userDto.getLastName()); // Set last name
        existingUser.setProfilePicture(userDto.getProfilePicture()); // Set profile picture
        return usersRepository.save(existingUser); // Save and return the user
    }
}
