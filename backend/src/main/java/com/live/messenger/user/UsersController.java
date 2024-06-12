package com.live.messenger.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for handling user-related operations.
 */
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService; // Service for handling user operations

    /**
     * Endpoint for creating a new user.
     *
     * @param userDto the user data transfer object containing the user details
     * @return a ResponseEntity with the status of the user creation
     */
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserDto userDto) {
        Users newUser = usersService.createUser(userDto);
        if (newUser != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("User added");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create user");
        }
    }

    /**
     * Endpoint for updating an existing user.
     *
     * @param id      the ID of the user to update
     * @param userDto the user data transfer object containing the updated user details
     * @return a ResponseEntity with the status of the user update
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable int id, @RequestBody UserDto userDto) {
        Users updateUser = usersService.updateUser(id, userDto);
        if (updateUser != null) {
            return ResponseEntity.ok("User with ID " + id + " has been updated");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID " + id + " not found");
        }
    }

    /**
     * Endpoint for deleting an existing user.
     *
     * @param id the ID of the user to delete
     * @return a ResponseEntity with the status of the user deletion
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        usersService.deleteUser(id);
        return ResponseEntity.ok("User with ID " + id + " has been deleted");
    }

    /**
     * Endpoint for retrieving a user by its ID.
     *
     * @param id the ID of the user to retrieve
     * @return a ResponseEntity containing the user details or an error message if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        UserDto userDto = usersService.getUserDto(id);
        if (userDto != null) {
            return ResponseEntity.ok(userDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID " + id + " not found");
        }
    }
}
