package com.live.messenger.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserDto userDto) {
        Users newUser = usersService.createUser(userDto);
        if (newUser != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(" User added ");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(" Failed to create user ");
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable int id, @RequestBody UserDto userDto) {
        Users updateUser = usersService.updateUser(id, userDto);
        if (updateUser != null) {
            return ResponseEntity.ok(" User with ID " + id + " has been updated");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(" User with ID " + id + " not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        usersService.deleteUser(id);
        return ResponseEntity.ok(" User with ID " + id + " has been deleted");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        UserDto userDto = usersService.getUserDto(id);
        if (userDto != null) {
            return ResponseEntity.ok(userDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(" User with ID " + id + " not found ");
        }
    }
}
